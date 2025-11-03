package br.com.maisprati.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "\"ExerciseList\"")
// @Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"titulo\"", nullable = false)
    private String titulo;

    @Column(name = "\"descricao\"", nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "exerciseListId")
    private List<Exercise> exercicios;


    // @Column(name = "\"grupos\"")
    @OneToMany(
            mappedBy = "exerciseList",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ExerciseGroup> grupos;

}
