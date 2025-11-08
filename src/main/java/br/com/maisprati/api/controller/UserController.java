package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.UserRequestDto;
import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getProfile(@AuthenticationPrincipal User user) {
        UserResponseDto response = userService.getProfile(user);
        return ResponseEntity.status(200).body(response);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/profile")
    public ResponseEntity<UserResponseDto> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UserRequestDto dto) {
        UserResponseDto updated = userService.updateProfile(user, dto);
        return ResponseEntity.status(200).body(updated);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    // NOVO: BUSCAR POR ID (ADMIN OU PRÓPRIO)
    @PreAuthorize("hasRole('ADMIN') OR principal.id == #id")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable Integer id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.findByIdDto(id));
    }
}
