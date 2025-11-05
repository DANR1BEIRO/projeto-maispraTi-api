package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.ExerciseList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseListRepository extends JpaRepository<ExerciseList, Integer> {

    Optional<ExerciseList> findByTitulo(String titulo);


}
