package br.com.maisprati.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"UserExerciseResult\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExerciseResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Associacao com User
    @ManyToOne
    @JoinColumn(name = "\"userId\"", nullable = false)
    private User user;

    // Associacao com Exercise
    @ManyToOne
    @JoinColumn(name = "\"exerciseId\"", nullable = false)
    private Exercise exercise;

    @Column(name = "\"foiCorreto\"", nullable = false)
    private boolean isCorrect;
}
