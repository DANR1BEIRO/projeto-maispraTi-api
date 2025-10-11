package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.RegisterDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Cria um construtor com todos os campos `final`
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registra um novo usuário no sistema.
     * Realiza a validação de e-mail duplicado e criptografa a senha antes de salvar.
     *
     * @param dados DTO contendo nome, e-mail e senha do novo usuário.
     * @return um DTO com os dados do usuário recém-criado, sem a senha.
     * @throws RuntimeException se o e-mail já estiver em uso.
     */
    public UserResponseDto register(RegisterDto dados) {
        if (userRepository.findByEmail(dados.getEmail()) != null) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        String senhaCriptografada = passwordEncoder.encode(dados.getSenha());

        User novoUsuario = new User(
                null, // id é null porque o DB vai gerá-lo automaticamente
                dados.getNomeCompleto(),
                dados.getEmail(),
                senhaCriptografada,
                null,
                0
        );

        User usuarioSalvo = userRepository.save(novoUsuario);

        return new UserResponseDto(usuarioSalvo);
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
