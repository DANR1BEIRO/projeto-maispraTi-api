package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
