package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionCode;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.permission.PermissionName;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.MenuJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.PermissionJpaEntity;
import jakarta.validation.constraints.NotNull;

public class PermissionMapper {

    public static Permission toDomain(@NotNull PermissionJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null (PermissionMapper)");
        
        MenuId menuId = null;
        if (jpaEntity.getMenu() != null) {
            menuId = new MenuId(jpaEntity.getMenu().getId());
        }
        
        return new Permission(
                jpaEntity.getId() != null ? new PermissionId(jpaEntity.getId()) : null,
                new PermissionName(jpaEntity.getName()),
                new PermissionCode(jpaEntity.getCode()),
                jpaEntity.getDescription(),
                menuId,
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
        
        // Set menu reference (lazy loaded)
        if (domainEntity.getMenuId() != null) {
            MenuJpaEntity menuJpa = new MenuJpaEntity();
            menuJpa.setId(domainEntity.getMenuId().value());
            jpaEntity.setMenu(menuJpa);
        }
        
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
