package br.com.maisprati.api.model;

import br.com.maisprati.api.enuns.ProgressStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"UserAnswer\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "id_exercicio")
    private Exercise exerciseId;

    @ManyToOne
    @JoinColumn(name = "id_exercise_list")
    private ExerciseList exerciseListId;

    @Column(name = "resposta_usuario")
    private String userAnswer;

    @Column(name = "resposta_correta")
    private Boolean correctAnswer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProgressStatusEnum status;

    public Boolean isCorrectAnswer() {
        return getCorrectAnswer();
    }
}
