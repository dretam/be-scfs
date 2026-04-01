package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.token.Token;
import bank_mega.corsys.domain.model.token.TokenHash;
import bank_mega.corsys.domain.model.token.TokenId;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.TokenJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class TokenMapper {

    public static Token toDomain(@NotNull TokenJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null (TokenMapper)");
        return new Token(
                new TokenId(jpaEntity.getId()),
                UserMapper.toDomain(jpaEntity.getUser()),
                new TokenHash(jpaEntity.getHash()),
                jpaEntity.getType(),
                jpaEntity.getExpiresAt(),
                jpaEntity.getLastUsingAt(),
                jpaEntity.getRevokeAt()
        );
    }

    public static Token toDomain(@NotNull TokenJpaEntity jpaEntity, Set<String> expands) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null (TokenMapper)");
        return new Token(
                new TokenId(jpaEntity.getId()),
                expands.contains("user") && jpaEntity.getUser() != null
                        ? UserMapper.toDomain(jpaEntity.getUser())
                        : null,
                new TokenHash(jpaEntity.getHash()),
                jpaEntity.getType(),
                jpaEntity.getExpiresAt(),
                jpaEntity.getLastUsingAt(),
                jpaEntity.getRevokeAt()
        );
    }

    public static TokenJpaEntity toJpaEntity(Token token) {
        TokenJpaEntity jpaEntity = new TokenJpaEntity();
        if (token.getId() != null) {
            jpaEntity.setId(token.getId().value());
        }
        jpaEntity.setUser(UserMapper.toJpaEntity(token.getUser()));
        jpaEntity.setHash(token.getHash().value());
        jpaEntity.setType(token.getType());
        jpaEntity.setExpiresAt(token.getExpiresAt());
        jpaEntity.setLastUsingAt(token.getLastUsingAt());
        jpaEntity.setRevokeAt(token.getRevokedAt());
        return jpaEntity;
    }

}
