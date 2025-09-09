package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.RegisterDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody RegisterDto dados) {
        UserResponseDto usuarioCadastrado = authService.register(dados);
        return ResponseEntity.status(201).body(usuarioCadastrado);
    }
}


