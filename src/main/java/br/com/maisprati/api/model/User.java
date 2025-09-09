package br.com.maisprati.api.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "users") // Nome da tabela no plural para evitar conflito com a keyword 'USER' no PostgreSQL.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeCompleto;

    @Column(unique = true)
    private String email;

    private String senhaHash;
    private String fotoPerfil;
    private Integer streakAtual;
}
