package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.ExerciseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseGroupRepository extends JpaRepository<ExerciseGroup, Integer> {

    // busca um grupo de exercicios pelo titulo
    Optional<ExerciseGroup> findByTitulo(String titulo);

    /**
     * Busca o próximo grupo:
     * Encontra o grupo com a menor ordem que é MAIOR que a ordem fornecida.
     */
    Optional<ExerciseGroup> findTopByOrdemGreaterThanOrderByOrdemAsc(Integer ordem);
}
