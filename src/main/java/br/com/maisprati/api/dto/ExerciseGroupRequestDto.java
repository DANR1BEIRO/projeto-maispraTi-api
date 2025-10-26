package br.com.maisprati.api.dto;

import lombok.Data;

@Data
public class ExerciseGroupRequestDto {

    private String titulo;
    private Integer ordem;
    private Integer listaId;
}
