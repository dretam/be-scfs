package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import bank_mega.corsys.domain.model.token.TokenType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tokens")
public class TokenJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String hash;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "token_type", nullable = false)
    private TokenType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "last_using_at")
    private Instant lastUsingAt;

    @Column(name = "revoke_at")
    private Instant revokeAt;

}
