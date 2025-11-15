package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.Exercise;
import br.com.maisprati.api.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {

    UserAnswer findByUserId_IdAndExerciseId_Id(Integer userId, Integer exerciseId);
    Optional<UserAnswer> findByExerciseId_Id(Integer id);
    Optional<UserAnswer> findFirstByExerciseId_Id(Integer exerciseId);
    List<UserAnswer> findAllByUserId_Id(Integer idUsuario);
    List<UserAnswer> findAllByUserId_IdAndExerciseListId_IdOrderByIdAsc(Integer userId, Integer ExerciseListId);
    List<UserAnswer> findAllByUserId_IdAndExerciseListId_IdOrderByExerciseId_IdAsc(
            Integer userId,
            Integer exerciseListId
    );
    List<UserAnswer> findAllByUserId_IdAndCorrectAnswerTrue(Integer idUsuario);
}
