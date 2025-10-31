package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.Exercise;
import br.com.maisprati.api.model.ExerciseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Exercise findByPergunta(String pergunta);

    /**
     * Conta quantos exercícios existem em um grupo específico.
     * Necessário para a validação do avanço de jornada.
     * (O campo de relacionamento na entidade Exercise é 'grupo')
     */
    int countByGrupo(ExerciseGroup grupo);
}