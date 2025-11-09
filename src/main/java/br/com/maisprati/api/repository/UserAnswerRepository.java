package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {

}
