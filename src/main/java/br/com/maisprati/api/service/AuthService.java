package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.LoginResponseDto;
import br.com.maisprati.api.dto.RegisterDto;
import br.com.maisprati.api.model.Role;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.RoleRepository;
import br.com.maisprati.api.repository.UserRepository;
import br.com.maisprati.api.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

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
        novoUsuario.setNome(dados.getNome());
        novoUsuario.setEmail(dados.getEmail());
        novoUsuario.setSenhaHash(passwordEncoder.encode(dados.getSenha()));

        User usuarioSalvo = userRepository.save(novoUsuario);

        Role rolePadrao = new Role();
        rolePadrao.setUser(usuarioSalvo);
        rolePadrao.setRole("USER");
        roleRepository.save(rolePadrao);

        usuarioSalvo.setRoles(List.of(rolePadrao));
        return new UserResponseDto(usuarioSalvo);
    }

    public LoginResponseDto obterToken(User usuarioAutenticado) {
        String token = jwtProvider.generateToken(usuarioAutenticado);
        return new LoginResponseDto(token);
    }

    public UserResponseDto getUserById(Integer id){
        Optional<User> userById = userRepository.findById(id);

        if(userById.isEmpty()){
            System.out.println("Usuário não existe");
        }

        User userResponse = userRepository.getReferenceById(id);

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(userResponse.getId());
        userResponseDto.setNomeCompleto(userResponse.getNomeCompleto());
        userResponseDto.setEmail(userResponse.getEmail());
        userResponseDto.setFotoPerfil(userResponse.getFotoPerfil());
        userResponseDto.setStreakAtual(userResponse.getStreakAtual());

        return userResponseDto;
    }

    public List<UserResponseDto> buscarUsuarios() {
        List<User> usuarios = userRepository.findAll();
        List<UserResponseDto> listUsers = new ArrayList<>();
        for(int i = 0; i < usuarios.size(); i++){
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(usuarios.get(i).getId());
            userResponseDto.setNomeCompleto(usuarios.get(i).getNomeCompleto());
            userResponseDto.setEmail(usuarios.get(i).getEmail());
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