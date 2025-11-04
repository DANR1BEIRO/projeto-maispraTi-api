package br.com.maisprati.api.model;

import br.com.maisprati.api.enuns.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "\"UserSystem\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"nomeCompleto\"", nullable = false)
    private String nomeCompleto;

    @Column(name = "\"email\"", unique = true, nullable = false)
    private String email;

    @Column(name = "\"senhaHash\"", nullable = false)
    private String senhaHash;

    @Column(name = "\"fotoPerfil\"")
    private String fotoPerfil;

    /**
     * No banco Postgres usamos ENUM, mas o H2 (banco de testes) não entende enums customizados.
     * Por isso anotei com @Enumerated(EnumType.STRING) sem @Type(PostgreSQLEnumType.class),
     * garantindo compatibilidade com os dois ambientes.
     */
    @Enumerated(EnumType.STRING)
    //@Type(PostgreSQLEnumType.class)
    @Column(name = "\"role\"", columnDefinition = "role")
    private RoleEnum role;

    @Column(name = "\"streakAtual\"")
    private Integer streakAtual;

    @Column(name = "\"grupoAtualId\"")
    private Integer grupoAtualId;

    @JsonIgnore // evita loop infinito na serializacao
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserExerciseResult> results;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return this.senhaHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}