package br.com.maisprati.api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExerciseListResponseDto {
    private Integer id;
    private String titulo;
    private String descricao;
    private List<Integer> exerciciosIds;
}
