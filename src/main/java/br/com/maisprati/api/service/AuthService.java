package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.LoginResponseDto;
import br.com.maisprati.api.dto.RegisterDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.enuns.RoleEnum;
import br.com.maisprati.api.mapper.UserMapper;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.UserRepository;
import br.com.maisprati.api.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return user;
    }

    public UserResponseDto register(RegisterDto dados) {
        if (userRepository.findByEmail(dados.getEmail()) != null) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        User novoUsuario = new User();
        novoUsuario.setNomeCompleto(dados.getNomeCompleto());
        novoUsuario.setEmail(dados.getEmail());
        novoUsuario.setSenhaHash(passwordEncoder.encode(dados.getSenha()));
        novoUsuario.setRole(RoleEnum.ALUNO);
        novoUsuario.setStreakAtual(0);
        novoUsuario.setFotoPerfil(dados.getFotoPerfil());
        novoUsuario.setCreatedAt(LocalDateTime.now());

        User usuarioSalvo = userRepository.save(novoUsuario);

        return new UserResponseDto(usuarioSalvo);
    }

    public LoginResponseDto obterToken(User usuarioAutenticado) {
        String token = jwtProvider.generateToken(usuarioAutenticado);
        return new LoginResponseDto(token);
    }

    // Agora o metodo lança exceção se o usuário não existir,
    // evitando null e fluxo inconsistente
    public UserResponseDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não existe"));

        UserResponseDto userResponseDto = mapper.toResponse(user);
        return userResponseDto;
    }

    public List<UserResponseDto> buscarUsuarios() {
        List<User> usuarios = userRepository.findAll();
        List<UserResponseDto> listUsers = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++) {
//            UserResponseDto userResponseDto = new UserResponseDto();
//            userResponseDto.setId(usuarios.get(i).getId());
//            userResponseDto.setNome(usuarios.get(i).getNome());
//            userResponseDto.setEmail(usuarios.get(i).getEmail());
            UserResponseDto userResponseDto = mapper.toResponse(usuarios.get(i));
            listUsers.add(userResponseDto);

        }
        //Outra forma de fazer:
//        return userRepository.findAll().stream()
//                .map(usuario -> new UserResponseDto(
//                        usuario.getId(),
//                        usuario.getNomeCompleto(),
//                        usuario.getEmail()
//                ))
//                .toList();

        return listUsers;
    }
}