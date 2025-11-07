package br.com.maisprati.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "progresso_jornada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JourneyProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "lista_atual", length = 50)
    private String listaAtual;

    /**
     * Lista de títulos das listas já concluídas pelo usuário.
     * Armazenada em tabela auxiliar via @ElementCollection.
     */
    @ElementCollection
    @CollectionTable(
            name = "progresso_listas_concluidas",
            joinColumns = @JoinColumn(name = "progresso_id"))
    @Column(name = "lista_concluida")
    private List<String> listasDeExerciciosConcluidos = new ArrayList<>();
}



