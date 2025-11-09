package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.UserRequestDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // lista todos (admin)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .toList();
    }

    // bosca por id e retorna dto
    public UserResponseDto findByIdDto(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
        return new UserResponseDto(user);
    }

    // busca por id e retorna entidade retorna entidade
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public UserResponseDto getProfile(User user) {
        return new UserResponseDto(user);
    }

    public UserResponseDto updateProfile(User user, UserRequestDto dto) {
        user.setNomeCompleto(dto.getNomeCompleto());
        user.setFotoPerfil(dto.getFotoPerfil());
        userRepository.save(user);
        return new UserResponseDto(user);
    }
}
