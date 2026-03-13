package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuId;
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

import java.util.*;
import java.util.stream.Collectors;

public class RoleMapper {

    public static Role toDomain(@NotNull RoleJpaEntity jpaEntity) {
        return toDomain(jpaEntity, null);
    }

    public static Role toDomain(RoleJpaEntity entity, Set<String> expands) {

        Role role = new Role(
                new RoleId(entity.getId()),
                new RoleName(entity.getName()),
                new RoleCode(entity.getCode()),
                new RoleIcon(entity.getIcon()),
                entity.getDescription(),
                AuditTrailEmbeddableMapper.toDomain(entity.getAudit())
        );

        if (expands != null && expands.contains("permissions")) {

            Set<Permission> permissions = new HashSet<>();
            Map<Long, Menu> menuCache = new HashMap<>();

            for (PermissionJpaEntity permissionJpa : entity.getPermissions()) {

                Permission permission = PermissionMapper.toDomain(permissionJpa);
                permissions.add(permission);

                if (permissionJpa.getMenu() != null) {

                    MenuJpaEntity menuJpa = permissionJpa.getMenu();
                    Long menuId = menuJpa.getId();

                    menuCache.computeIfAbsent(
                            menuId,
                            id -> MenuMapper.toDomain(menuJpa)
                    );
                }
            }

            role.setPermissions(permissions);

            if (expands.contains("menus")) {

                List<Menu> flatMenus = new ArrayList<>(menuCache.values());

                List<Menu> treeMenus = buildMenuTree(flatMenus);

                role.setMenus(new HashSet<>(treeMenus));
            }
        }

        return role;
    }


    private static List<Menu> buildMenuTree(List<Menu> menus) {

        Map<Long, Menu> menuMap = menus.stream()
                .collect(Collectors.toMap(
                        m -> m.getId().value(),
                        m -> {
                            m.setChildren(new ArrayList<>());
                            return m;
                        }
                ));

        List<Menu> rootMenus = new ArrayList<>();

        for (Menu menu : menus) {

            MenuId parentId = menu.getParentId();

            if (parentId == null) {
                rootMenus.add(menu);
            } else {

                Menu parent = menuMap.get(parentId.value());

                if (parent != null) {
                    parent.getChildren().add(menu);
                }
            }
        }

        return rootMenus;
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

        return jpaEntity;
    }

}
