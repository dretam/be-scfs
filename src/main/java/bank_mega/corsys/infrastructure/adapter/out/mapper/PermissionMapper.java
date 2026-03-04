package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionCode;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.permission.PermissionName;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.PermissionJpaEntity;
import jakarta.validation.constraints.NotNull;

public class PermissionMapper {

    public static Permission toDomain(@NotNull PermissionJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        return new Permission(
                jpaEntity.getId() != null ? new PermissionId(jpaEntity.getId()) : null,
                new PermissionName(jpaEntity.getName()),
                new PermissionCode(jpaEntity.getCode()),
                jpaEntity.getDescription(),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static PermissionJpaEntity toJpaEntity(Permission domainEntity) {
        PermissionJpaEntity jpaEntity = new PermissionJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setId(domainEntity.getId().value());
        }
        jpaEntity.setName(domainEntity.getName().value());
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setDescription(domainEntity.getDescription());
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
