package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleIcon;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import jakarta.validation.constraints.NotNull;

public class RoleMapper {

    public static Role toDomain(@NotNull RoleJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        return new Role(
                new RoleId(jpaEntity.getId()),
                new RoleName(jpaEntity.getName()),
                new RoleIcon(jpaEntity.getIcon()),
                jpaEntity.getDescription(),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static RoleJpaEntity toJpaEntity(Role domainEntity) {
        RoleJpaEntity jpaEntity = new RoleJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setId(domainEntity.getId().value());
        }
        jpaEntity.setName(domainEntity.getName().value());
        jpaEntity.setDescription(domainEntity.getDescription());
        jpaEntity.setIcon(domainEntity.getIcon().value());
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
