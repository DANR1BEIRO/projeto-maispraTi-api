package br.com.maisprati.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nomeCompleto;

    @Email(message = "E-mail deve ser válido")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    @NotBlank(message= "Senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;
    private String fotoPerfil;
}
