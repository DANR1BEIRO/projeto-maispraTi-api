package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.JourneyProgress;
import br.com.maisprati.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JourneyProgressRepository extends JpaRepository<JourneyProgress, Integer> {

    Optional<JourneyProgress> findByUser(User user);
}
