package br.com.maisprati.api.dto;

import lombok.Data;

@Data
public class ExerciseGroupResponseDto {

    private Integer id;
    private String titulo;
    private Integer ordem;
    private Integer listaId;
}
