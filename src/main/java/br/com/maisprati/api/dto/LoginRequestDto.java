package br.com.maisprati.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para receber as credenciais de login.
 * Define o "contrato" que o endpoint /login espera receber.
 *
 * @param login O email do usuário para autenticação.
 * @param senha A senha do usuário.
 */

public record LoginRequestDto(

        @NotBlank(message = "O email é obrigatório.")

        @Email(message = "O formato do email é inválido.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        String senha
) {
}
