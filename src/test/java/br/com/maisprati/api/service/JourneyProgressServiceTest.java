package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.JourneyProgressResponseDto;
import br.com.maisprati.api.model.JourneyProgress;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.JourneyProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        progress.setGrupoAtual("Grupo 1");
        progress.setGruposConcluidos(new ArrayList<>(List.of("Grupo 1")));
    }

    @Test
    void deveRetornarProgressoQuandoUsuarioEProgressoExistem() {
        // mocks
        when(userService.findById(1)).thenReturn(Optional.of(user));
        when(journeyProgressRepository.findByUser(user)).thenReturn(Optional.of(progress));

        // exec
        JourneyProgressResponseDto result = journeyProgressService.consultarProgresso(1);

        // asserts
        assertNotNull(result);
        assertEquals("Grupo 1", result.getGrupoAtual());
        assertEquals(1, result.getGruposConcluidos().size());
        assertTrue(result.getGruposConcluidos().contains("Grupo 1"));

        verify(userService).findById(1);
        verify(journeyProgressRepository).findByUser(user);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(userService.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> journeyProgressService.consultarProgresso(1));

        verify(userService).findById(1);
        verify(journeyProgressRepository, never()).findByUser(any());
    }

    @Test
    void deveLancarExcecaoQuandoProgressoNaoExiste() {
        when(userService.findById(1)).thenReturn(Optional.of(user));
        when(journeyProgressRepository.findByUser(user)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> journeyProgressService.consultarProgresso(1));

        verify(userService).findById(1);
        verify(journeyProgressRepository).findByUser(user);
    }
}
