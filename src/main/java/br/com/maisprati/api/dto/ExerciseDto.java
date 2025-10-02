package br.com.maisprati.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExerciseDto {
    private String grupo;
    private String pergunta;
    private List<String> alternativas;
    private String respostaCorreta;
}
