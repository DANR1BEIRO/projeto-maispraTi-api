package br.com.maisprati.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "\"Exercise\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "grupo", nullable = false)
    private String grupo;

    @Column(unique = true)
    private String pergunta;

    private List<String> alternativas;

    @Column(name = "resposta_correta")
    private String respostaCorreta;
}
