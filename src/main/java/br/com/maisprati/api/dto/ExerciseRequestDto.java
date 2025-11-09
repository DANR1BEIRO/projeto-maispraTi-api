package br.com.maisprati.api.dto;

import br.com.maisprati.api.enuns.ExerciseTypeEnum;
//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ExerciseRequestDto {

    @NotBlank(message = "Pergunta é obrigatório")
    private String pergunta;

    @NotNull(message = "Tipo é obrigatório")
    private ExerciseTypeEnum tipo;

    private List<String> alternativas;

    @NotBlank(message = "Resposta correta é obrigatório")
    private String respostaCorreta;

    @NotNull(message = "Id da lista é obrigatório")
    private Integer listaId;
}
