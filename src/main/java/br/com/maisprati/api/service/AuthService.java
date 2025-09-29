package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.LoginResponseDto;
import br.com.maisprati.api.dto.RegisterDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.UserRepository;
import br.com.maisprati.api.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public UserResponseDto register(RegisterDto dados) {
        if (userRepository.findByEmail(dados.getEmail()) != null) {
            throw new RuntimeException("E-mail já cadastrado!");
        }
        String senhaCriptografada = passwordEncoder.encode(dados.getSenha());
        User novoUsuario = new User(
                null, dados.getNome(), dados.getEmail(), senhaCriptografada, null, 0
        );
        User usuarioSalvo = userRepository.save(novoUsuario);
        return new UserResponseDto(usuarioSalvo);
    }

    public LoginResponseDto obterToken(User usuarioAutenticado) {
        String token = jwtProvider.generateToken(usuarioAutenticado);
        return new LoginResponseDto(token);
    }
}