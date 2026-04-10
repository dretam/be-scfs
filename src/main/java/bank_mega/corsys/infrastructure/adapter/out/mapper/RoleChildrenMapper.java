package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleIcon;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenIcon;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenName;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.MenuJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.PermissionJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleChildrenJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleChildrenMapper {

    public static RoleChildren toDomain(@NotNull RoleChildrenJpaEntity jpaEntity) {
        return toDomain(jpaEntity, null);
    }

    public static RoleChildren toDomain(RoleChildrenJpaEntity entity, Set<String> expands) {
        if (entity == null) {
            return null;
        }

        RoleChildren roleChildren = new RoleChildren(
                new RoleChildrenName(entity.getName()),
                new RoleChildrenCode(entity.getCode()),
                entity.getIcon() != null ? new RoleChildrenIcon(entity.getIcon()) : null,
                RoleMapper.toDomain(entity.getRole()),
                entity.getDescription(),
                AuditTrailEmbeddableMapper.toDomain(entity.getAudit())
        );

        if (expands != null) {
            if (expands.contains("permissions") && entity.getPermissions() != null) {
                Set<Permission> permissions = entity.getPermissions().stream()
                        .map(PermissionMapper::toDomain)
                        .collect(Collectors.toSet());
                roleChildren.setPermissions(permissions);
            }

            if (expands.contains("menus") && entity.getMenus() != null) {
                List<Menu> flatMenus = entity.getMenus().stream()
                        .map(MenuMapper::toDomain)
                        .toList();

                List<Menu> treeMenus = MenuMapper.buildMenuTree(flatMenus);
                roleChildren.setMenus(new HashSet<>(treeMenus));
            }
        }

        return roleChildren;
    }

    public static RoleChildrenJpaEntity toJpaEntity(RoleChildren domainEntity) {
        if (domainEntity == null) {
            return null;
        }

        RoleChildrenJpaEntity jpaEntity = new RoleChildrenJpaEntity();

        if (domainEntity.getCode() != null) {
            jpaEntity.setCode(domainEntity.getCode().value());
        }

        if (domainEntity.getName() != null) {
            jpaEntity.setName(domainEntity.getName().value());
        }

        jpaEntity.setDescription(domainEntity.getDescription());

        if (domainEntity.getIcon() != null) {
            jpaEntity.setIcon(domainEntity.getIcon().value());
        }

        jpaEntity.setRole(RoleMapper.toJpaEntity(domainEntity.getRole()));

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