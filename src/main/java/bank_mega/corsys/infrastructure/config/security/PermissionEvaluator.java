package bank_mega.corsys.infrastructure.config.security;

import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.user.User;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for evaluating user permissions.
 * Can be used in service layer for programmatic permission checks.
 */
public class PermissionEvaluator {

    /**
     * Check if the user has the specified permission.
     *
     * @param user the user to check
     * @param permissionCode the permission code (e.g., "USER_CREATE")
     * @return true if user has the permission, false otherwise
     */
    public static boolean hasPermission(User user, String permissionCode) {
        if (user == null || user.getRole() == null) {
            return false;
        }

        // Super user has all permissions
        if ("ROLE_SU".equals(user.getRole().getName().value())) {
            return true;
        }

        Set<String> userPermissions = user.getRole().getPermissions().stream()
                .map(permission -> permission.getCode().value())
                .collect(Collectors.toSet());

        return userPermissions.contains(permissionCode);
    }

    /**
     * Check if the user has any of the specified permissions.
     *
     * @param user the user to check
     * @param permissionCodes the permission codes to check
     * @return true if user has at least one permission, false otherwise
     */
    public static boolean hasAnyPermission(User user, String... permissionCodes) {
        if (user == null || user.getRole() == null) {
            return false;
        }

        // Super user has all permissions
        if ("ROLE_SU".equals(user.getRole().getName().value())) {
            return true;
        }

        Set<String> userPermissions = user.getRole().getPermissions().stream()
                .map(permission -> permission.getCode().value())
                .collect(Collectors.toSet());

        for (String permissionCode : permissionCodes) {
            if (userPermissions.contains(permissionCode)) {
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
    public static boolean hasAllPermissions(User user, String... permissionCodes) {
        if (user == null || user.getRole() == null) {
            return false;
        }

        // Super user has all permissions
        if ("ROLE_SU".equals(user.getRole().getName().value())) {
            return true;
        }

        Set<String> userPermissions = user.getRole().getPermissions().stream()
                .map(permission -> permission.getCode().value())
                .collect(Collectors.toSet());

        for (String permissionCode : permissionCodes) {
            if (!userPermissions.contains(permissionCode)) {
                return false;
            }
        }

        return true;
    }

}
