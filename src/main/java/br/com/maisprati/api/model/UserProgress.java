package br.com.maisprati.api.model;

import br.com.maisprati.api.enuns.PostgreSQLEnumType;
import br.com.maisprati.api.enuns.PostgreSQLProgressStatusEnumType;
import br.com.maisprati.api.enuns.ProgressStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "\"UserProgress\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_exercicio")
    private Exercise exercise;

    @Enumerated(EnumType.STRING)
    private ProgressStatusEnum status;
}
