package br.com.maisprati.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "\"ExerciseGroup\"")
// @Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"titulo\"")
    private String title;

    @Column(name = "\"ordem\"")
    private Integer sequence;

//    @Column(name = "\"listaId\"")
//    private Integer listId;

    @ManyToOne
    @JoinColumn(name = "\"listaId\"", nullable = false)
    @JsonIgnore
    private ExerciseList exerciseList;

    //    @Column(name = "exercicios")
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises;
}
