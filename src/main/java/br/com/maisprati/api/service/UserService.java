package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.UserRequestDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
