package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Exercise findByPergunta(String pergunta);
}