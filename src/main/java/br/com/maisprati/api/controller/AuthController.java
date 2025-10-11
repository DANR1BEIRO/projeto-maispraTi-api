package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.RegisterDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> buscarUsuarios(){
        List<UserResponseDto> userResponseDto = authService.buscarUsuarios();
        return ResponseEntity.status(201).body(userResponseDto);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id){
        UserResponseDto userResponseDto = authService.getUserById(id);
        return ResponseEntity.status(201).body(userResponseDto);
    }


}


