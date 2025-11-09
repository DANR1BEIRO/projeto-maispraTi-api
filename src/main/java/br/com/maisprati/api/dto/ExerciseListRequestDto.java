package br.com.maisprati.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExerciseListRequestDto {

    private String titulo;
    private String descricao;
//    private List<ExerciseRequestDto> lista;
}
