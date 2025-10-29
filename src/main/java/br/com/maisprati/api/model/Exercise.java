package br.com.maisprati.api.model;

import br.com.maisprati.api.enuns.ExerciseTypeEnum;
import br.com.maisprati.api.enuns.PostgreSQLExerciseTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "\"Exercise\"")
// @Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise extends BaseEntity {

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

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "\"grupo_id\"")
    private ExerciseGroup grupo;

    @JsonIgnore
    @OneToMany(
            mappedBy = "exercise",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserExerciseResult> results;
}
