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

//public class UserProgress extends BaseEntity{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne
//    @JoinColumn(name = "\"userId\"")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "\"exerciseId\"")
//    private Exercise exercise;
//
//    @Type(PostgreSQLProgressStatusEnumType.class)
//    @Column(columnDefinition = "ProgressStatus", name = "\"status\"", nullable = false)
//    private ProgressStatusEnum status; // DISPONIVEL, BLOQUEADO, CONCLUIDO
//
//    @Column(name = "\"respondidoCorretamente\"", nullable = false)
//    private Boolean respondidoCorretamente;
//
//}

//        id                     Int  @id @default(autoincrement())
//        userId                 Int
//        user                   User @relation(fields: [userId], references: [id])
//        exerciseId             Int
//        exercise               Exercise @relation(fields: [exerciseId], references: [id])
//        status                 ProgressStatus @default(BLOQUEADO)
//        respondidoCorretamente Boolean        @default(false)
//        updatedAt              DateTime       @updatedAt
