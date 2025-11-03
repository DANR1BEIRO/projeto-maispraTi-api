package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.RegisterDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.security.JwtAuthenticationFilter;
import br.com.maisprati.api.service.AuthService;
import br.com.maisprati.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private AuthService authService;

    // ADICIONE ESTE MOCK:
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void deveRegistrarUsuarioComSucesso() throws Exception {
        RegisterDto dto = new RegisterDto();
        dto.setNomeCompleto("Daniel");
        dto.setEmail("daniel@if.com");
        dto.setSenha("123456");
        dto.setFotoPerfil("foto.png");

        User usuario = new User();
        usuario.setId(1);
        usuario.setNomeCompleto("Daniel");
        usuario.setEmail("daniel@if.com");
        usuario.setFotoPerfil("foto.png");
        usuario.setStreakAtual(0);

        UserResponseDto response = new UserResponseDto(usuario);

        when(authService.register(any(RegisterDto.class))).thenReturn(response);

        doAnswer(invocation -> {
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(), any(), any());
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    System.out.println("JSON RETORNADO: " + content);
                })
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Daniel"))
                .andExpect(jsonPath("$.email").value("daniel@if.com"));
    }
}