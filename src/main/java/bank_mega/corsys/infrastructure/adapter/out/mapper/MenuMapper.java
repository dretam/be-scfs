package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuCode;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.menu.MenuName;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.MenuJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.PermissionJpaEntity;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.util.Set;

public class MenuMapper {

    public static Menu toDomain(@NotNull MenuJpaEntity jpaEntity) {
        return toDomain(jpaEntity, null);
    }

    public static Menu toDomain(@NotNull MenuJpaEntity jpaEntity, Set<String> expands) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        Menu menu = new Menu(
                jpaEntity.getId() != null ? new MenuId(jpaEntity.getId()) : null,
                new MenuName(jpaEntity.getName()),
                new MenuCode(jpaEntity.getCode()),
                jpaEntity.getPath(),
                jpaEntity.getIcon(),
                jpaEntity.getParentId() != null ? new MenuId(jpaEntity.getParentId()) : null,
                jpaEntity.getSortOrder(),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );

        boolean loadPermissions = expands == null || expands.contains("permissions");

        if (loadPermissions && jpaEntity.getPermissions() != null && Hibernate.isInitialized(jpaEntity.getPermissions())) {
            for (PermissionJpaEntity permissionJpa : jpaEntity.getPermissions()) {
                Permission permission = PermissionMapper.toDomain(permissionJpa);
                menu.addPermission(permission);
            }
        }

        return menu;
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
