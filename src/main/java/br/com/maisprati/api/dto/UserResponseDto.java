package br.com.maisprati.api.dto;

import br.com.maisprati.api.enuns.RoleEnum;
import br.com.maisprati.api.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String nome;
    private String email;
    private String fotoPerfil;
    private RoleEnum role;
    private List<Integer> exerciciosCompletos;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nome = user.getNomeCompleto();
        this.email = user.getEmail();
        this.fotoPerfil = user.getFotoPerfil();
        this.role = user.getRole();
    }
}
