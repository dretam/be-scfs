package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.user.*;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class UserMapper {

    public static User toDomain(@NotNull UserJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        return new User(
                new UserId(jpaEntity.getId()),
                new UserName(jpaEntity.getName()),
                new UserEmail(jpaEntity.getEmail()),
                new UserPassword(jpaEntity.getPassword()),
                RoleMapper.toDomain(jpaEntity.getRole()),
                jpaEntity.getType(),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static User toDomain(@NotNull UserJpaEntity jpaEntity, Set<String> expands) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        return new User(
                new UserId(jpaEntity.getId()),
                new UserName(jpaEntity.getName()),
                new UserEmail(jpaEntity.getEmail()),
                new UserPassword(jpaEntity.getPassword()),
                expands.contains("role") && jpaEntity.getRole() != null
                        ? RoleMapper.toDomain(jpaEntity.getRole())
                        : null,
                jpaEntity.getType(),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static UserJpaEntity toJpaEntity(User user) {
        UserJpaEntity jpaEntity = new UserJpaEntity();
        if (user.getId() != null) {
            jpaEntity.setId(user.getId().value());
        }
        jpaEntity.setName(user.getName().value());
        jpaEntity.setEmail(user.getEmail().value());
        jpaEntity.setPassword(user.getPassword().value());
        jpaEntity.setRole(RoleMapper.toJpaEntity(user.getRole()));
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(user.getAudit()));
        return jpaEntity;
    }

}
