//package bank_mega.corsys.infrastructure.config.seeder;
//
//import bank_mega.corsys.domain.exception.DomainRuleViolationException;
//import bank_mega.corsys.domain.model.common.AuditTrail;
//import bank_mega.corsys.domain.model.menu.Menu;
//import bank_mega.corsys.domain.model.menu.MenuCode;
//import bank_mega.corsys.domain.model.menu.MenuId;
//import bank_mega.corsys.domain.model.menu.MenuName;
//import bank_mega.corsys.domain.model.permission.Permission;
//import bank_mega.corsys.domain.model.permission.PermissionCode;
//import bank_mega.corsys.domain.model.permission.PermissionName;
//import bank_mega.corsys.domain.model.role.Role;
//import bank_mega.corsys.domain.model.role.RoleCode;
//import bank_mega.corsys.domain.model.role.RoleIcon;
//import bank_mega.corsys.domain.model.role.RoleName;
//import bank_mega.corsys.domain.repository.MenuRepository;
//import bank_mega.corsys.domain.repository.PermissionRepository;
//import bank_mega.corsys.domain.repository.RoleRepository;
//import lombok.RequiredArgsConstructor;
//import jakarta.validation.constraints.NotNull;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//@Order(1)
//public class RbacSeeder implements ApplicationRunner {
//
//    private final PermissionRepository permissionRepository;
//    private final MenuRepository menuRepository;
//    private final RoleRepository roleRepository;
//
//    @Override
//    public void run(@NotNull ApplicationArguments args) {
//
//        if (roleRepository.count() == 0) {
//            seedRoles();
//        }
//
//        if (menuRepository.count() == 0) {
//            seedMenus();
//        }
//
//        if (permissionRepository.count() == 0) {
//            seedPermissions();
//        }
//
//        seedRoleAssignments();
//    }
//
//    /**
//     * ======================
//     * ROLE SEEDER
//     * ======================
//     */
//    private void seedRoles() {
//
//        createRole("ROLE_APP", "APPLICANT", "lucide:cog", "For app only, used for scheduler");
//        createRole("ROLE_SU", "SUPER USER", "lucide:crown", "Super user that have all privileges, used by developer");
//        createRole("ROLE_ADMIN", "ADMIN", "lucide:clipboard-pen-line", "Administration privileges");
//        createRole("ROLE_VIEW", "VIEWER", "lucide:eye", "View only privileges");
//    }
//
//    private void createRole(String code, String name, String icon, String description) {
//
//        Role role = new Role(
//                new RoleName(name),
//                new RoleCode(code),
//                new RoleIcon(icon),
//                description,
//                AuditTrail.create(0L)
//        );
//
//        roleRepository.save(role);
//    }
//
//    /**
//     * ======================
//     * MENU SEEDER
//     * ======================
//     */
//    private void seedMenus() {
//
//        createMenu("Dashboard", "MENU_DASHBOARD", "/dashboard", "lucide:layout-dashboard", null, 1);
//
//        createMenu("User Management", "MENU_USER_MANAGEMENT", "/users", "lucide:users", null, 2);
//
//        createMenu("Role Management", "MENU_ROLE_MANAGEMENT", "/roles", "lucide:shield", null, 3);
//
//        createMenu("Permission Management", "MENU_PERMISSION_MANAGEMENT", "/permissions", "lucide:key", null, 4);
//
//        createMenu("Menu Management", "MENU_MENU_MANAGEMENT", "/menus", "lucide:menu", null, 5);
//
//        createMenu("Parameter Management", "MENU_PARAMETER_MANAGEMENT", "/parameters", "lucide:settings", null, 6);
//    }
//
//    private Menu createMenu(
//            String name,
//            String code,
//            String path,
//            String icon,
//            Long parentId,
//            Integer sortOrder
//    ) {
//
//        Menu menu = new Menu(
//                null,
//                new MenuName(name),
//                new MenuCode(code),
//                path,
//                icon,
//                parentId != null ? new MenuId(parentId) : null,
//                sortOrder,
//                AuditTrail.create(0L)
//        );
//
//        return menuRepository.save(menu);
//    }
//
//    /**
//     * ======================
//     * PERMISSION SEEDER
//     * ======================
//     */
//    private void seedPermissions() {
//
//        Map<String, String[]> permissionMap = Map.of(
//
//                "MENU_USER_MANAGEMENT", new String[]{
//                        "USER_CREATE", "USER_READ", "USER_UPDATE", "USER_DELETE"
//                },
//
//                "MENU_ROLE_MANAGEMENT", new String[]{
//                        "ROLE_CREATE", "ROLE_READ", "ROLE_UPDATE", "ROLE_DELETE"
//                },
//
//                "MENU_PERMISSION_MANAGEMENT", new String[]{
//                        "PERMISSION_CREATE", "PERMISSION_READ", "PERMISSION_UPDATE", "PERMISSION_DELETE"
//                },
//
//                "MENU_MENU_MANAGEMENT", new String[]{
//                        "MENU_CREATE", "MENU_READ", "MENU_UPDATE", "MENU_DELETE"
//                },
//
//                "MENU_DASHBOARD", new String[]{
//                        "DASHBOARD_VIEW"
//                },
//
//                "MENU_PARAMETER_MANAGEMENT", new String[]{
//                        "PARAMETER_CREATE", "PARAMETER_READ", "PARAMETER_UPDATE", "PARAMETER_DELETE"
//                }
//        );
//
//        permissionMap.forEach((menuCode, permissionCodes) -> {
//
//            Menu menu = menuRepository
//                    .findFirstByCode(new MenuCode(menuCode))
//                    .orElseThrow(() ->
//                            new DomainRuleViolationException("Menu not found: " + menuCode));
//
//            for (String code : permissionCodes) {
//
//                String name = formatPermissionName(code);
//
//                Permission permission = new Permission(
//                        null,
//                        new PermissionName(name),
//                        new PermissionCode(code),
//                        "Permission to " + name.toLowerCase(),
//                        menu.getId(),
//                        AuditTrail.create(0L)
//                );
//
//                permissionRepository.save(permission);
//            }
//        });
//    }
//
//    /**
//     * ======================
//     * ROLE PERMISSION ASSIGNMENT
//     * ======================
//     */
//    private void seedRoleAssignments() {
//
//        Role suRole = getRole("ROLE_SU");
//        Role adminRole = getRole("ROLE_ADMIN");
//        Role viewRole = getRole("ROLE_VIEW");
//
//        List<Permission> allPermissions =
//                permissionRepository
//                        .findAllPageable(1, 1000, "audit.createdAt", "")
//                        .getContent();
//
//        List<Menu> allMenus =
//                menuRepository
//                        .findAllPageable(1, 1000, "sortOrder", "", null)
//                        .getContent();
//
//        /**
//         * SUPER USER
//         */
//        if (suRole != null) {
//
//            allPermissions.forEach(suRole::addPermission);
//            allMenus.forEach(suRole::addMenu);
//
//            suRole.updateAudit(0L);
//            roleRepository.save(suRole);
//        }
//
//        /**
//         * ADMIN
//         */
//        if (adminRole != null) {
//
//            List<Permission> adminPermissions = allPermissions.stream()
//                    .filter(p -> {
//
//                        String code = p.getCode().value();
//
//                        return code.startsWith("USER_")
//                               || code.startsWith("ROLE_")
//                               || code.equals("PERMISSION_READ")
//                               || code.startsWith("MENU_")
//                               || code.equals("DASHBOARD_VIEW")
//                               || code.startsWith("PARAMETER_");
//                    })
//                    .toList();
//
//            adminPermissions.forEach(adminRole::addPermission);
//            allMenus.forEach(adminRole::addMenu);
//
//            adminRole.updateAudit(0L);
//            roleRepository.save(adminRole);
//        }
//
//        /**
//         * VIEW ONLY
//         */
//        if (viewRole != null) {
//
//            List<Permission> viewPermissions = allPermissions.stream()
//                    .filter(p -> {
//
//                        String code = p.getCode().value();
//
//                        return code.equals("USER_READ")
//                               || code.equals("ROLE_READ")
//                               || code.equals("PERMISSION_READ")
//                               || code.equals("MENU_READ")
//                               || code.equals("DASHBOARD_VIEW")
//                               || code.equals("PARAMETER_READ");
//                    })
//                    .toList();
//
//            viewPermissions.forEach(viewRole::addPermission);
//            allMenus.forEach(viewRole::addMenu);
//
//            viewRole.updateAudit(0L);
//            roleRepository.save(viewRole);
//        }
//    }
//
//    private Role getRole(String roleName) {
//        return roleRepository
//                .findFirstByName(new RoleName(roleName))
//                .orElse(null);
//    }
//
//    private String formatPermissionName(String code) {
//        return code.replace("_", " ");
//    }
//}