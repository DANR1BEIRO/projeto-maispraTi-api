package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Exercise findByPergunta(String pergunta);

//    List<Exercise> findByGrupoId(Integer grupoId);
}