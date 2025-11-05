package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.ExerciseGroup;
import br.com.maisprati.api.model.ExerciseList;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.model.UserExerciseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserExerciseResultRepository extends JpaRepository<UserExerciseResult, Integer> {
    /**
     * Conta o número de exercícios concluídos com sucesso (isCorrect = true)
     * por um usuário dentro de um grupo de exercícios específico.
     */
    @Query("SELECT COUNT(r) FROM UserExerciseResult r WHERE r.user = :user AND r.exercise.grupo = :exerciseGroup AND r.isCorrect = true")
    int countSuccessfulByUserAndList(
            @Param("user") User user,
            @Param("exerciseGroup") ExerciseList list
    );
}
