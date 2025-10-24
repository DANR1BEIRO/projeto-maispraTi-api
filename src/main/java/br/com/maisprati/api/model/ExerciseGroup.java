package br.com.maisprati.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"ExerciseGroup\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"titulo\"")
    private String title;

    @Column(name = "\"ordem\"")
    private Integer sequence;

    @Column(name = "\"listaId\"")
    private Integer listId;
}
