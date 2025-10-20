package br.com.maisprati.api.model;

import br.com.maisprati.api.enuns.ExerciseTypeEnum;
import br.com.maisprati.api.enuns.PostgreSQLEnumType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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

    @Type(PostgreSQLEnumType.class)
    @Column(name = "tipo")
    private ExerciseTypeEnum tipo;

    @Column(name = "pergunta", unique = true)
    private String pergunta;

    @Column(name = "opcoes")
    private List<String> alternativas;

    @Column(name = "resposta_correta")
    private String respostaCorreta;

    //Deu ruim, transformar na tabela o grupoId para snakcase grupo_id
    @Column(name = "grupoId")
    private Integer grupoId;
}
