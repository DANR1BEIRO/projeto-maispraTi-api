package br.com.maisprati.api.dto;

import lombok.Data;

@Data
public class UserAnswerResponseDto {
//    private Integer id;
//    private Integer idUsuario;
//    private Integer idExercicio;
//    private String respostaUsuario;
    private String menssagem;
    private boolean respostaCorreta;
    private String respostaCorretaExercise;
}
