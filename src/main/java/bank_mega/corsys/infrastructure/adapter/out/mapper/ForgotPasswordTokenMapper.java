package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.forgotpasstoken.ForgotToken;
import bank_mega.corsys.domain.model.forgotpasstoken.ForgotTokenHash;
import bank_mega.corsys.domain.model.forgotpasstoken.ForgotTokenId;
import bank_mega.corsys.domain.model.forgotpasstoken.ForgotTokenUsed;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ForgotPasswordTokenJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class ForgotPasswordTokenMapper {
    public static ForgotToken toDomain(@NotNull ForgotPasswordTokenJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null (ForgotPasswordTokenMapper)");
        return new ForgotToken(
                new ForgotTokenId(jpaEntity.getId()),
                UserMapper.toDomain(jpaEntity.getUser()),
                new ForgotTokenHash(jpaEntity.getTokenHash()),
                jpaEntity.getExpiresAt(),
                new ForgotTokenUsed(jpaEntity.getUsed()),
                AuditTrail.create(jpaEntity.getUser().getId())
        );
    }

    public static ForgotToken toDomain(@NotNull ForgotPasswordTokenJpaEntity jpaEntity, Set<String> expands) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null (ForgotPasswordTokenMapper)");
        return new ForgotToken(
                new ForgotTokenId(jpaEntity.getId()),
                expands.contains("user") && jpaEntity.getUser() != null
                        ? UserMapper.toDomain(jpaEntity.getUser())
                        : null,
                new ForgotTokenHash(jpaEntity.getTokenHash()),
                jpaEntity.getExpiresAt(),
                new ForgotTokenUsed(jpaEntity.getUsed()),
                AuditTrail.create(jpaEntity.getUser().getId())
        );
    }

    public static ForgotPasswordTokenJpaEntity toJpaEntity(ForgotToken token) {
        ForgotPasswordTokenJpaEntity jpaEntity = new ForgotPasswordTokenJpaEntity();
        if (token.getForgotTokenId() != null) {
            jpaEntity.setId(token.getForgotTokenId().value());
        }
        jpaEntity.setUser(UserMapper.toJpaEntity(token.getUser()));
        jpaEntity.setTokenHash(token.getForgotTokenHash().value());
        jpaEntity.setExpiresAt(token.getExpiresAt());
        jpaEntity.setUsed(token.getForgotTokenUsed().value());
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(token.getAudit()));
        return jpaEntity;
    }
}
