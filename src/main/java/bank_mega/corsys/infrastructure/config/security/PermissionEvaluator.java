package bank_mega.corsys.infrastructure.config.security;

import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.userpermission.Effect;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import bank_mega.corsys.domain.repository.UserPermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Component for evaluating user permissions.
 * Can be used in service layer for programmatic permission checks.
 *
 * Permission Resolution Formula:
 * FINAL_PERMISSIONS = (role_permissions + user_allow_permissions) - user_deny_permissions
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final UserPermissionRepository userPermissionRepository;

    /**
     * Check if the user has the specified permission.
     * Considers role permissions and user-level overrides.
     *
     * @param user the user to check
     * @param permissionCode the permission code (e.g., "USER_CREATE")
     * @return true if user has the permission, false otherwise
     */
    public boolean hasPermission(User user, String permissionCode) {

        if (user == null || user.getRole() == null) {
            return false;
        }

        // Super user has all permissions
        String role = user.getRole().getName().value();
        if ("ROLE_SU".equals(role)) {
            return true;
        }

        Set<String> rolePermissions = user.getRole().getPermissions().stream()
                .map(permission -> permission.getCode().value())
                .collect(Collectors.toSet());

        // Get user permission overrides from repository
        Set<String> userAllowPermissions = getUserAllowPermissions(user);
        Set<String> userDenyPermissions = getUserDenyPermissions(user);
        // Apply formula: FINAL = (role_perms + user_allow) - user_deny
        boolean hasFromRole = rolePermissions.contains(permissionCode);
        boolean hasFromAllow = userAllowPermissions.contains(permissionCode);
        boolean hasDeny = userDenyPermissions.contains(permissionCode);

        // DENY always wins
        if (hasDeny) {
            return false;
        }

        // ALLOW if from role OR from user allow override
        return hasFromRole || hasFromAllow;
    }

    /**
     * Check if the user has any of the specified permissions.
     *
     * @param user the user to check
     * @param permissionCodes the permission codes to check
     * @return true if user has at least one permission, false otherwise
     */
    public boolean hasAnyPermission(User user, String... permissionCodes) {
        if (user == null || user.getRole() == null) {
            return false;
        }

        // Super user has all permissions
        if ("ROLE_SU".equals(user.getRole().getName().value())) {
            return true;
        }

        for (String permissionCode : permissionCodes) {
            if (hasPermission(user, permissionCode)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if the user has all of the specified permissions.
     *
     * @param user the user to check
     * @param permissionCodes the permission codes to check
     * @return true if user has all permissions, false otherwise
     */
    public boolean hasAllPermissions(User user, String... permissionCodes) {
        if (user == null || user.getRole() == null) {
            return false;
        }

        // Super user has all permissions
        if ("ROLE_SU".equals(user.getRole().getName().value())) {
            return true;
        }

        for (String permissionCode : permissionCodes) {
            if (!hasPermission(user, permissionCode)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Get the final effective permissions for a user.
     * Formula: (role_permissions + user_allow) - user_deny
     */
    /**
     * Get the final effective permissions for a user.
     * Formula: (role_permissions + user_allow) - user_deny
     */
    public Set<Permission> getEffectivePermissions(User user) {
        if (user == null || user.getRole() == null) {
            return Set.of();
        }

        Set<Permission> effectivePermissions = new HashSet<>(user.getRole().getPermissions());

        List<UserPermission> userPermissions = userPermissionRepository.findAllByUserId(
                new UserId(user.getId().value())
        );

        Set<Permission> allowPermissions = new HashSet<>();
        Set<String> denyPermissionCodes = new HashSet<>();

        for (UserPermission userPermission : userPermissions) {
            if (userPermission.isAllow()) {
                allowPermissions.add(userPermission.getPermission());
            } else {
                denyPermissionCodes.add(userPermission.getPermission().getCode().value());
            }
        }

        effectivePermissions.addAll(allowPermissions);
        effectivePermissions.removeIf(permission ->
                denyPermissionCodes.contains(permission.getCode().value())
        );

        return effectivePermissions;
    }

    /**
     * Extract ALLOW override permissions from user.
     */
    private Set<String> getUserAllowPermissions(User user) {
        return userPermissionRepository.findAllAllowByUserId(new UserId(user.getId().value())).stream()
                .map(up -> up.getPermission().getCode().value())
                .collect(Collectors.toSet());
    }

    /**
     * Extract DENY override permissions from user.
     */
    private Set<String> getUserDenyPermissions(User user) {
        return userPermissionRepository.findAllDenyByUserId(new UserId(user.getId().value())).stream()
                .map(up -> up.getPermission().getCode().value())
                .collect(Collectors.toSet());
    }

}
