package br.com.maisprati.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(

        @NotBlank(message = "O email é obrigatório.")

        @Email(message = "O formato do email é inválido.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        String senha
) {
}
