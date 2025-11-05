package br.com.maisprati.api.model;

import jakarta.persistence.Column;

public class UserAnswer {
    @Column(name = "id")
    private Integer id;

    private User userId;
    private Exercise exerciseId;

    @Column(name = "resposta_usuario")
    private String userAnswer;
}
