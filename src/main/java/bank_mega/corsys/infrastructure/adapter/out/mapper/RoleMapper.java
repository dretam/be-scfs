package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleIcon;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.MenuJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.PermissionJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {

    public static Role toDomain(@NotNull RoleJpaEntity jpaEntity) {
        return toDomain(jpaEntity, null);
    }

    public static Role toDomain(@NotNull RoleJpaEntity jpaEntity, Set<String> expands) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        
        RoleCode roleCode = null;
        if (jpaEntity.getCode() != null && !jpaEntity.getCode().isBlank()) {
            roleCode = new RoleCode(jpaEntity.getCode());
        }
        
        Role role = new Role(
                new RoleId(jpaEntity.getId()),
                new RoleName(jpaEntity.getName()),
                roleCode,
                new RoleIcon(jpaEntity.getIcon()),
                jpaEntity.getDescription(),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );

        // Map permissions if loaded and initialized
        boolean loadPermissions = expands == null || expands.contains("permissions");
        if (loadPermissions && jpaEntity.getPermissions() != null && Hibernate.isInitialized(jpaEntity.getPermissions())) {
            for (PermissionJpaEntity permissionJpa : jpaEntity.getPermissions()) {
                Permission permission = PermissionMapper.toDomain(permissionJpa);
                role.addPermission(permission);
            }
        }

        // Map menus if loaded and initialized
        boolean loadMenus = expands == null || expands.contains("menus");
        if (loadMenus && jpaEntity.getMenus() != null && Hibernate.isInitialized(jpaEntity.getMenus())) {
            for (MenuJpaEntity menuJpa : jpaEntity.getMenus()) {
                Menu menu = MenuMapper.toDomain(menuJpa);
                role.addMenu(menu);
            }
        }

        return role;
    }

    public static RoleJpaEntity toJpaEntity(Role domainEntity) {
        RoleJpaEntity jpaEntity = new RoleJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setId(domainEntity.getId().value());
        }
        jpaEntity.setName(domainEntity.getName().value());
        
        // Set code if present
        if (domainEntity.getCode() != null) {
            jpaEntity.setCode(domainEntity.getCode().value());
        }
        
        jpaEntity.setDescription(domainEntity.getDescription());
        jpaEntity.setIcon(domainEntity.getIcon().value());
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));

        // Map permissions
        if (domainEntity.getPermissions() != null && !domainEntity.getPermissions().isEmpty()) {
            Set<PermissionJpaEntity> permissionJpaEntities = domainEntity.getPermissions().stream()
                    .map(PermissionMapper::toJpaEntity)
                    .collect(Collectors.toSet());
            jpaEntity.setPermissions(permissionJpaEntities);
        }

        // Map menus
        if (domainEntity.getMenus() != null && !domainEntity.getMenus().isEmpty()) {
            Set<MenuJpaEntity> menuJpaEntities = domainEntity.getMenus().stream()
                    .map(MenuMapper::toJpaEntity)
                    .collect(Collectors.toSet());
            jpaEntity.setMenus(menuJpaEntities);
        }

        return jpaEntity;
    }

}
