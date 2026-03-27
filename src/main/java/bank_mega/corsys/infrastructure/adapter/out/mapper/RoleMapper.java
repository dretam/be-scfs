package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleIcon;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.MenuJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.PermissionJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class RoleMapper {

    public static Role toDomain(@NotNull RoleJpaEntity jpaEntity) {
        return toDomain(jpaEntity, null);
    }

    public static Role toDomain(RoleJpaEntity entity, Set<String> expands) {
        if (entity == null) {
            return null;
        }

        Role role = new Role(
                new RoleName(entity.getName()),
                new RoleCode(entity.getCode()),
                entity.getIcon() != null ? new RoleIcon(entity.getIcon()) : null,
                entity.getDescription(),
                AuditTrailEmbeddableMapper.toDomain(entity.getAudit())
        );

        if (expands != null) {
            if (expands.contains("permissions") && entity.getPermissions() != null) {
                Set<Permission> permissions = entity.getPermissions().stream()
                        .map(PermissionMapper::toDomain)
                        .collect(Collectors.toSet());
                role.setPermissions(permissions);
            }

            if (expands.contains("menus") && entity.getMenus() != null) {
                List<Menu> flatMenus = entity.getMenus().stream()
                        .map(MenuMapper::toDomain)
                        .toList();

                List<Menu> treeMenus = MenuMapper.buildMenuTree(flatMenus);
                role.setMenus(new HashSet<>(treeMenus));
            }
        }

        return role;
    }

    public static RoleJpaEntity toJpaEntity(Role domainEntity) {
        if (domainEntity == null) {
            return null;
        }

        RoleJpaEntity jpaEntity = new RoleJpaEntity();

        if (domainEntity.getCode() != null) {
            jpaEntity.setId(domainEntity.getCode().value());
        }

        if (domainEntity.getName() != null) {
            jpaEntity.setName(domainEntity.getName().value());
        }

        // Set code if present
        if (domainEntity.getCode() != null) {
            jpaEntity.setCode(domainEntity.getCode().value());
        }

        jpaEntity.setDescription(domainEntity.getDescription());

        if (domainEntity.getIcon() != null) {
            jpaEntity.setIcon(domainEntity.getIcon().value());
        }

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));

        // Map permissions
        if (domainEntity.getPermissions() != null && !domainEntity.getPermissions().isEmpty()) {
            Set<PermissionJpaEntity> permissionJpaEntities = domainEntity.getPermissions().stream()
                    .filter(Objects::nonNull)
                    .map(PermissionMapper::toJpaEntity)
                    .collect(Collectors.toSet());
            jpaEntity.setPermissions(permissionJpaEntities);
        }

        // Map menus if present in domain entity
        if (domainEntity.getMenus() != null && !domainEntity.getMenus().isEmpty()) {
            Set<MenuJpaEntity> menuJpaEntities = domainEntity.getMenus().stream()
                    .filter(Objects::nonNull)
                    .map(MenuMapper::toJpaEntity)
                    .collect(Collectors.toSet());
            jpaEntity.setMenus(menuJpaEntities);
        }

        return jpaEntity;
    }
}