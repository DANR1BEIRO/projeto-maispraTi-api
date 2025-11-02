package br.com.maisprati.api.dto;

import br.com.maisprati.api.model.ExerciseGroup;
import br.com.maisprati.api.model.ExerciseList;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExerciseListResponseDto {
    private Integer id;
    private String titulo;
    private String descricao;
    private List<ExerciseGroupResponseDto> grupos;
    private List<ExerciseResponseDto> exercicios;
}
