package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.Exercise;
import br.com.maisprati.api.model.ExerciseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Exercise findByPergunta(String pergunta);

    // TODO: Adaptar consulta quando a jornada passar a usar ExerciseList em vez de grupo
    // Exemplo futuro:
    // List<Exercise> findByExerciseListId(ExerciseList list);
}