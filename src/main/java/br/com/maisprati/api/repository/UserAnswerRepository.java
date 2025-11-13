package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {

    Optional<UserAnswer> findFirstByExerciseId_Id(Integer exerciseId);
//    Optional<UserAnswer> findByUserId_Id(Integer idUsuario);
    List<UserAnswer> findAllByUserId_IdAndCorrectAnswerTrue(Integer idUsuario);
}
