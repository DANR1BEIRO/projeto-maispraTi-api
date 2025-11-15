package br.com.maisprati.api.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserAnswerResponseDto {
//    private Integer id;
//    private Integer idUsuario;
//    private Integer idExercicio;
//    private String respostaUsuario;
    private String menssagem;
    private Boolean respostaCorreta;
    private String respostaCorretaExercise;
}
