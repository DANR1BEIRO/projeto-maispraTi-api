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

    @Column(name = "grupo_atual", length = 50)
    private String grupoAtual;

    /**
     * @ElementCollection: indica que o campo não é um relacionamento com outra entidade,
     * mas uma coleção de elementos simples
     * Sinaliza para o hibernate que ele deve criar uma nova tabela no db para
     * armazenar os valores da lista.
     */
    @ElementCollection
    @CollectionTable(
            name = "grupos_concluidos_progresso",
            joinColumns = @JoinColumn(name = "progresso_id"))
    @Column(name = "grupo")
    private List<String> gruposConcluidos = new ArrayList<>();
}
