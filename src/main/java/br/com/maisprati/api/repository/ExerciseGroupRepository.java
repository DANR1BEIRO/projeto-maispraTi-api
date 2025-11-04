package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.ExerciseGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExerciseGroupRepository extends JpaRepository<ExerciseGroup, Integer> {

    // Observação: algumas consultas aqui são nativas porque JPQL não suporta LIMIT.
    // Isso garante compatibilidade com o Postgres em produção e H2 nos testes.

    // Busca grupo pelo título usando JPQL (suportado por Postgres e H2).
    @Query("SELECT e FROM ExerciseGroup e WHERE e.title = :titulo")
    Optional<ExerciseGroup> findByTitulo(@Param("titulo") String titulo);

    /**
     * JPQL não suporta 'LIMIT', então para buscar o próximo grupo por ordem
     * precisamos usar uma query nativa. O Postgres aceita LIMIT e o H2 também,
     * garantindo que isso funcione tanto em produção quanto em testes.
     */
    @Query("SELECT e FROM ExerciseGroup e WHERE e.sequence > :ordem ORDER BY e.sequence ASC LIMIT 1")
    Optional<ExerciseGroup> findTopByOrdemGreaterThanOrderByOrdemAsc(@Param("ordem") Integer ordem);
}