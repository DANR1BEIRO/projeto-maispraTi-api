package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.JourneyProgressResponseDto;
import br.com.maisprati.api.security.JwtAuthenticationFilter;
import br.com.maisprati.api.service.JourneyProgressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = JourneyProgressController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class JourneyProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JourneyProgressService journeyProgressService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRetornarProgressoDoUsuario() throws Exception {

        doAnswer(invocation -> {
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(), any(), any());

        JourneyProgressResponseDto response = JourneyProgressResponseDto.builder()
                .grupoAtual("Grupo 1")
                .gruposConcluidos(List.of("Grupo 0"))
                .proximosGruposBloqueados(List.of("Grupo 2"))
                .build();

        when(journeyProgressService.consultarProgresso(1)).thenReturn(response);

        mockMvc.perform(get("/api/journey/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grupoAtual").value("Grupo 1"))
                .andExpect(jsonPath("$.gruposConcluidos[0]").value("Grupo 0"))
                .andExpect(jsonPath("$.proximosGruposBloqueados[0]").value("Grupo 2"));
    }
}
