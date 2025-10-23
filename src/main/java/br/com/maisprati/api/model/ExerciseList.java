package br.com.maisprati.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"ExerciseList\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseList extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"titulo\"", nullable = false)
    private String titulo;

    @Column(name = "\"descricao\"", nullable = false)
    private String descricao;

//    @Column(name = "\"grupos\"")
//    private List<ExerciseGroup> grupos;
}
