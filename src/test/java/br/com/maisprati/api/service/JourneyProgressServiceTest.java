package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.JourneyProgressResponseDto;
import br.com.maisprati.api.model.JourneyProgress;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.JourneyProgressRepository;
import br.com.maisprati.api.service.ExerciseGroupService;
import br.com.maisprati.api.service.JourneyProgressService;
import br.com.maisprati.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JourneyProgressServiceTest {

    @Mock
    private JourneyProgressRepository journeyProgressRepository;

    @Mock
    private UserService userService;

    @Mock
    private ExerciseGroupService exerciseGroupService;

    @InjectMocks
    private JourneyProgressService journeyProgressService;

    private User user;
    private JourneyProgress progress;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setNomeCompleto("Daniel");

        progress = new JourneyProgress();
        progress.setUser(user);
        progress.setGruposConcluidos(List.of("Grupo 1"));
    }

    @Test
    void deveRetornarProgressoQuandoUsuarioEProgressoExistem() {
        when(userService.getUserById(1)).thenReturn(user);
        when(journeyProgressRepository.findByUser(user)).thenReturn(Optional.of(progress));
        when(exerciseGroupService.calcularGruposBloqueados(any(), any()))
                .thenReturn(List.of("Grupo 2"));

        JourneyProgressResponseDto result = journeyProgressService.consultarProgresso(1);

        assertNotNull(result);
        assertEquals(1, result.gruposConcluidos().size());
        assertEquals(1, result.gruposBloqueados().size());
        assertTrue(result.gruposConcluidos().contains("Grupo 1"));
        assertTrue(result.gruposBloqueados().contains("Grupo 2"));

        verify(userService).getUserById(1);
        verify(journeyProgressRepository).findByUser(user);
    }
}
