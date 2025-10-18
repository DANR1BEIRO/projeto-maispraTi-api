package br.com.maisprati.api.dto;

import br.com.maisprati.api.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String nome;
    private String email;
    private String fotoPerfil;
    private Integer streakAtual;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nome = user.getNomeCompleto();
        this.email = user.getEmail();
        this.fotoPerfil = user.getFotoPerfil();
        this.streakAtual = user.getStreakAtual();
    }
}
