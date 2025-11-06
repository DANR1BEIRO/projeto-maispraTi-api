package br.com.maisprati.api.dto;

import jakarta.validation.constraints.NotNull;

public class UserAnswerRequestDto {
    @NotNull(message = "idUsuario é um campo obrigatório.")
    private Integer idUsuario;

    @NotNull(message = "idExercicio é um campo obrigatório.")
    private Integer idExercicio;

    @NotNull(message = "respostaUsuario é um campo obrigatório.")
    private Integer respostaUsuario;
}
