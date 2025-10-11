package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.Role;
import br.com.maisprati.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUser(User user);
}