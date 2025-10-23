package br.com.maisprati.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExerciseListResponseDto {
    private Integer id;
    private String titulo;
    private String descricao;
//    private List<ExerciseGroup> grupos;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
