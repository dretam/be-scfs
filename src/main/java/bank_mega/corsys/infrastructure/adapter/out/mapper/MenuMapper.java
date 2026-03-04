package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuCode;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.menu.MenuName;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.MenuJpaEntity;
import jakarta.validation.constraints.NotNull;

public class MenuMapper {

    public static Menu toDomain(@NotNull MenuJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        return new Menu(
                jpaEntity.getId() != null ? new MenuId(jpaEntity.getId()) : null,
                new MenuName(jpaEntity.getName()),
                new MenuCode(jpaEntity.getCode()),
                jpaEntity.getPath(),
                jpaEntity.getIcon(),
                jpaEntity.getParentId() != null ? new MenuId(jpaEntity.getParentId()) : null,
                jpaEntity.getSortOrder(),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static MenuJpaEntity toJpaEntity(Menu domainEntity) {
        MenuJpaEntity jpaEntity = new MenuJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setId(domainEntity.getId().value());
        }
        jpaEntity.setName(domainEntity.getName().value());
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setPath(domainEntity.getPath());
        jpaEntity.setIcon(domainEntity.getIcon());
        jpaEntity.setParentId(domainEntity.getParentId() != null ? domainEntity.getParentId().value() : null);
        jpaEntity.setSortOrder(domainEntity.getSortOrder());
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
