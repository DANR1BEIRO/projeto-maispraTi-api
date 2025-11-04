package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.JourneyProgress;
import br.com.maisprati.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JourneyProgressRepositoryTest {

    @Autowired
    private JourneyProgressRepository journeyProgressRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void deveEncontrarProgressoPorUsuario() {
        User user = new User();
        user.setNomeCompleto("Daniel");
        user.setEmail("daniel@if.com");
        user.setSenhaHash("123");
        userRepository.save(user);

        JourneyProgress progress = new JourneyProgress();
        progress.setUser(user);
        progress.setGrupoAtual("Grupo 1");
        progress.setGruposConcluidos(List.of("Grupo 0"));
        journeyProgressRepository.save(progress);

        var resultado = journeyProgressRepository.findByUser(user);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getGrupoAtual()).isEqualTo("Grupo 1");
        assertThat(resultado.get().getGruposConcluidos()).containsExactly("Grupo 0");
    }
}
