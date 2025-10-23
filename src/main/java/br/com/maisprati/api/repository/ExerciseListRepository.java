package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.ExerciseList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseListRepository extends JpaRepository<ExerciseList, Integer> {
}
