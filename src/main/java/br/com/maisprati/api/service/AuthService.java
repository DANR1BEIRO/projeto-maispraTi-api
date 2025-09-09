package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.RegisterDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
