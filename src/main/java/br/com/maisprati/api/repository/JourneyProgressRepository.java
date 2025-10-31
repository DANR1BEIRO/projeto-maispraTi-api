package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.JourneyProgress;
import br.com.maisprati.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JourneyProgressRepository extends JpaRepository<JourneyProgress, Integer> {
    /**
     * @param user user A entidade User do usuário logado
     * @return Optional contendo o JourneyProgress, se encontrado.
     * Optional para indicar que a busca pode retornar zero ou um resultado.
     * Isso força o código que chamar este metodo a tratar explicitamente o
     * caso em que o progresso não é encontrado, evitando NullPointerExceptions.
     */
    Optional<JourneyProgress> findByUser(User user);
}
