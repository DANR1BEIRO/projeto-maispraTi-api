package br.com.maisprati.api.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserAnswerResponseDto {

    private String menssagem;
    private Boolean respostaCorreta;
    private String respostaCorretaExercise;
}
