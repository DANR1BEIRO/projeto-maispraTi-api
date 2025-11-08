package br.com.maisprati.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "lista_exercicios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "ordem", nullable = false)
    private Integer ordem;


    @OneToMany(
            mappedBy = "exerciseListId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Exercise> exercicios;

}
