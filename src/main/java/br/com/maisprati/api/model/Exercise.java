package br.com.maisprati.api.model;

import br.com.maisprati.api.enuns.ExerciseTypeEnum;
import br.com.maisprati.api.enuns.PostgreSQLExerciseTypeEnum;
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

    @Type(PostgreSQLExerciseTypeEnum.class)
    @Column(name = "\"tipo\"", columnDefinition = "tipo")
    private ExerciseTypeEnum tipo;

    @Column(name = "\"pergunta\"", unique = true)
    private String pergunta;

    @Column(name = "\"opcoes\"")
    private List<String> alternativas;

    @Column(name = "\"resposta_correta\"")
    private String respostaCorreta;

    @Column(name = "\"grupo_id\"")
    private Integer grupoId;
}
