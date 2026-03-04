package bank_mega.corsys.infrastructure.config.seeder;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuCode;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.menu.MenuName;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionCode;
import bank_mega.corsys.domain.model.permission.PermissionName;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleIcon;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.repository.MenuRepository;
import bank_mega.corsys.domain.repository.PermissionRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RbacSeeder implements ApplicationRunner {

    private final PermissionRepository permissionRepository;
    private final MenuRepository menuRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(@NotNull ApplicationArguments args) {
        // Seed permissions
        if (permissionRepository.count() == 0) {
            seedPermissions();
        }

        // Seed menus
        if (menuRepository.count() == 0) {
            seedMenus();
        }

        // Seed role-permission and role-menu assignments
        seedRoleAssignments();
    }

    private void seedPermissions() {
        List<String> permissionCodes = List.of(
                // User permissions
                "USER_CREATE", "USER_READ", "USER_UPDATE", "USER_DELETE",
                // Role permissions
                "ROLE_CREATE", "ROLE_READ", "ROLE_UPDATE", "ROLE_DELETE",
                // Permission permissions
                "PERMISSION_CREATE", "PERMISSION_READ", "PERMISSION_UPDATE", "PERMISSION_DELETE",
                // Menu permissions
                "MENU_CREATE", "MENU_READ", "MENU_UPDATE", "MENU_DELETE"
        );

        for (String code : permissionCodes) {
            String name = formatPermissionName(code);
            Permission permission = new Permission(
                    null,
                    new PermissionName(name),
                    new PermissionCode(code),
                    "Permission to " + name.toLowerCase(),
                    AuditTrail.create(0L)
            );
            permissionRepository.save(permission);
        }
    }

    private void seedMenus() {
        // Parent menus (no parent)
        Menu dashboard = createMenu("Dashboard", "MENU_DASHBOARD", "/dashboard", "lucide:layout-dashboard", null, 1);
        Menu userManagement = createMenu("User Management", "MENU_USER_MANAGEMENT", "/users", "lucide:users", null, 2);
        Menu roleManagement = createMenu("Role Management", "MENU_ROLE_MANAGEMENT", "/roles", "lucide:shield", null, 3);
        Menu permissionManagement = createMenu("Permission Management", "MENU_PERMISSION_MANAGEMENT", "/permissions", "lucide:key", null, 4);
        Menu menuManagement = createMenu("Menu Management", "MENU_MENU_MANAGEMENT", "/menus", "lucide:menu", null, 5);
    }

    private Menu createMenu(String name, String code, String path, String icon, Long parentId, Integer sortOrder) {
        Menu menu = new Menu(
                null,
                new MenuName(name),
                new MenuCode(code),
                path,
                icon,
                parentId != null ? new MenuId(parentId) : null,
                sortOrder,
                AuditTrail.create(0L)
        );
        return menuRepository.save(menu);
    }

    private void seedRoleAssignments() {
        Map<String, Role> roles = Map.of(
                "ROLE_SU", getRole("ROLE_SU"),
                "ROLE_ADMIN", getRole("ROLE_ADMIN"),
                "ROLE_VIEW", getRole("ROLE_VIEW")
        );

        // ROLE_SU - All permissions
        Role suRole = roles.get("ROLE_SU");
        if (suRole != null) {
            List<Permission> allPermissions = permissionRepository.findAllPageable(1, 100, "audit.createdAt", "").getContent();
            for (Permission permission : allPermissions) {
                suRole.addPermission(permission);
            }

            List<Menu> allMenus = menuRepository.findAllPageable(1, 100, "sortOrder", "").getContent();
            for (Menu menu : allMenus) {
                suRole.addMenu(menu);
            }
            suRole.updateAudit(0L);
            roleRepository.save(suRole);
        }

        // ROLE_ADMIN - USER_*, ROLE_*, PERMISSION_READ, MENU_*
        Role adminRole = roles.get("ROLE_ADMIN");
        if (adminRole != null) {
            List<Permission> adminPermissions = permissionRepository.findAllPageable(1, 100, "audit.createdAt", "").getContent().stream()
                    .filter(p -> {
                        String code = p.getCode().value();
                        return code.startsWith("USER_") ||
                                code.startsWith("ROLE_") ||
                                code.equals("PERMISSION_READ") ||
                                code.startsWith("MENU_");
                    })
                    .toList();

            for (Permission permission : adminPermissions) {
                adminRole.addPermission(permission);
            }

            List<Menu> allMenus = menuRepository.findAllPageable(1, 100, "sortOrder", "").getContent();
            for (Menu menu : allMenus) {
                adminRole.addMenu(menu);
            }
            adminRole.updateAudit(0L);
            roleRepository.save(adminRole);
        }

        // ROLE_VIEW - USER_READ, ROLE_READ, PERMISSION_READ, MENU_READ
        Role viewRole = roles.get("ROLE_VIEW");
        if (viewRole != null) {
            List<Permission> viewPermissions = permissionRepository.findAllPageable(1, 100, "audit.createdAt", "").getContent().stream()
                    .filter(p -> {
                        String code = p.getCode().value();
                        return code.equals("USER_READ") ||
                                code.equals("ROLE_READ") ||
                                code.equals("PERMISSION_READ") ||
                                code.equals("MENU_READ");
                    })
                    .toList();

            for (Permission permission : viewPermissions) {
                viewRole.addPermission(permission);
            }

            List<Menu> allMenus = menuRepository.findAllPageable(1, 100, "sortOrder", "").getContent();
            for (Menu menu : allMenus) {
                viewRole.addMenu(menu);
            }
            viewRole.updateAudit(0L);
            roleRepository.save(viewRole);
        }
    }

    private Role getRole(String roleName) {
        return roleRepository.findFirstByName(new RoleName(roleName)).orElse(null);
    }

    private String formatPermissionName(String code) {
        return code.replace("_", " ");
    }

}
