package br.com.maisprati.api.dto;

import br.com.maisprati.api.enuns.ExerciseTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ExercisePutRequestDto {

        private String pergunta;
        private ExerciseTypeEnum tipo;
        private List<String> alternativas;
        private String respostaCorreta;
        private Integer grupoId;

}
