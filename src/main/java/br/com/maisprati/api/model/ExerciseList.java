package br.com.maisprati.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"ExerciseList\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseList extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordem_seq")
    @SequenceGenerator(name = "ordem_seq", sequenceName = "ordem_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "\"titulo\"", nullable = false)
    private String titulo;

    @Column(name = "\"descricao\"", nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "exerciseListId")
    private List<Exercise> exercicios = new ArrayList<>();

    //Porque parou de funcionar assim?
//    @OneToMany(mappedBy = "exerciseListId")
//    private List<Exercise> exercicios;

    @Column(name = "\"ordem\"", nullable = false)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordem_seq")
//    @SequenceGenerator(name = "ordem_seq", sequenceName = "ordem_seq", allocationSize = 1)
    private Integer ordem;


//    @OneToMany(
//            mappedBy = "exerciseList",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<Exercise> exercicios;

}
