package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.LoginResponseDto;
import br.com.maisprati.api.dto.RegisterDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.UserRepository;
import br.com.maisprati.api.security.JwtProvider;
import br.com.maisprati.api.mapper.UserMapper;
import br.com.maisprati.api.enuns.RoleEnum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        RegisterDto dto = new RegisterDto();
        dto.setNomeCompleto("Daniel");
        dto.setEmail("daniel@example.com");
        dto.setSenha("senha123");
        dto.setFotoPerfil("foto.png");

        when(passwordEncoder.encode("senha123")).thenReturn("senhaHashFake");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(null);

        User usuarioSalvo = new User();
        usuarioSalvo.setId(1);
        usuarioSalvo.setNomeCompleto(dto.getNomeCompleto());
        usuarioSalvo.setEmail(dto.getEmail());
        usuarioSalvo.setSenhaHash("senhaHashFake");
        usuarioSalvo.setRole(RoleEnum.ALUNO);

        when(userRepository.save(any(User.class))).thenReturn(usuarioSalvo);

        UserResponseDto expectedResponse = new UserResponseDto(usuarioSalvo);
        when(mapper.toResponse(usuarioSalvo)).thenReturn(expectedResponse);

        UserResponseDto result = authService.register(dto);

        assertNotNull(result);
        assertEquals(dto.getNomeCompleto(), result.getNome());
        assertEquals(dto.getEmail(), result.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(dto.getSenha());
    }

    @Test
    void naoDeveCadastrarUsuarioComEmailJaExistente() {
        RegisterDto dto = new RegisterDto();
        dto.setEmail("daniel@example.com");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(new User());

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> authService.register(dto));

        assertEquals("E-mail já cadastrado!", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deveRetornarUsuarioQuandoEncontrado() {
        User usuario = new User();
        usuario.setEmail("daniel@if.com");

        when(userRepository.findByEmail("daniel@if.com"))
                .thenReturn(usuario);

        UserDetails resultado = authService.loadUserByUsername("daniel@if.com");

        assertNotNull(resultado);
        assertEquals("daniel@if.com", resultado.getUsername());
        verify(userRepository, times(1)).findByEmail("daniel@if.com");
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> authService.getUserById(999));

        verify(userRepository).findById(999);
        verify(mapper, never()).toResponse(any());
    }

    @Test
    void deveGerarTokenParaUsuarioAutenticado() {
        User usuario = new User();
        usuario.setEmail("daniel@if.com");

        when(jwtProvider.generateToken(usuario))
                .thenReturn("token-teste-123");
        LoginResponseDto resposta = authService.obterToken(usuario);
        assertNotNull(resposta);
        assertEquals("token-teste-123", resposta.token());
        verify(jwtProvider, times(1)).generateToken(usuario);
    }

    @Test
    void deveBuscarUsuarioPorIdComSucesso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNomeCompleto("Daniel");
        usuario.setEmail("daniel@if.com");

        when(userRepository.findById(1)).thenReturn(Optional.of(usuario));

        UserResponseDto responseDto = new UserResponseDto(usuario);
        when(mapper.toResponse(usuario)).thenReturn(responseDto);

        UserResponseDto result = authService.getUserById(1);
        assertNotNull(result);
        assertEquals("Daniel", result.getNome());
        assertEquals("daniel@if.com", result.getEmail());

        verify(userRepository).findById(1);
        verify(mapper).toResponse(usuario);
    }

    @Test
    void deveBuscarTodosUsuariosComSucesso() {
        User usuario1 = new User();
        usuario1.setId(1);
        usuario1.setNomeCompleto("Daniel");
        usuario1.setEmail("daniel@if.com");

        User usuario2 = new User();
        usuario2.setId(2);
        usuario2.setNomeCompleto("João");
        usuario2.setEmail("joao@if.com");

        when(userRepository.findAll()).thenReturn(List.of(usuario1, usuario2));

        when(mapper.toResponse(any(User.class))).thenAnswer(invocation -> {
            User arg = invocation.getArgument(0);
            if ("daniel@if.com".equals(arg.getEmail())) return new UserResponseDto(usuario1);
            if ("joao@if.com".equals(arg.getEmail())) return new UserResponseDto(usuario2);
            return null;
        });

        List<UserResponseDto> result = authService.buscarUsuarios();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.stream().anyMatch(u -> "Daniel".equals(u.getNome())));
        assertTrue(result.stream().anyMatch(u -> "João".equals(u.getNome())));

        verify(userRepository).findAll();
        verify(mapper, times(2)).toResponse(any(User.class));
    }
}
