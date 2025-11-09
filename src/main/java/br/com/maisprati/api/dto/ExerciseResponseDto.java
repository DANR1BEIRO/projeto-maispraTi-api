package br.com.maisprati.api.dto;

import br.com.maisprati.api.enuns.ExerciseTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class ExerciseResponseDto {
    private Integer id;
    private String pergunta;
    private List<String> alternativas;
    private String respostaCorreta;
    private Integer listaId;
    private ExerciseTypeEnum tipo;
}
