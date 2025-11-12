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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordem_seq")
    @SequenceGenerator(name = "ordem_seq", sequenceName = "ordem_seq", allocationSize = 1)
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
    @JoinColumn(name = "\"lista_id\"")
    @JsonIgnore
    private ExerciseList exerciseListId;

//    @Column(name = "\"ordem\"", nullable = false, unique = true)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordem_seq")
//    @SequenceGenerator(name = "ordem_seq", sequenceName = "ordem_seq", allocationSize = 1)
//    private Integer ordem;

    @JsonIgnore
    @OneToMany(
            mappedBy = "exercise",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserExerciseResult> results;
}
