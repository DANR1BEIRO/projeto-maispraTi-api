package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.LoginRequestDto;
import br.com.maisprati.api.dto.LoginResponseDto;
import br.com.maisprati.api.dto.RegisterDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid RegisterDto dados) {
        UserResponseDto usuarioCadastrado = authService.register(dados);
        return ResponseEntity.status(201).body(usuarioCadastrado);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        Authentication authentication = authenticationManager.authenticate(authToken);
        User usuarioAutenticado = (User) authentication.getPrincipal();
        LoginResponseDto responseDto = authService.obterToken(usuarioAutenticado);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserResponseDto(user));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> buscarUsuarios(){
        List<UserResponseDto> userResponseDto = authService.buscarUsuarios();
        return ResponseEntity.status(201).body(userResponseDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id){
        UserResponseDto userResponseDto = authService.getUserById(id);
        return ResponseEntity.status(201).body(userResponseDto);
    }
}