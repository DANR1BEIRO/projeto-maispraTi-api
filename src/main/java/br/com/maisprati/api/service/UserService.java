package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.UserRequestDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    // Usado no JourneyProgressServiceTest
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));
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
