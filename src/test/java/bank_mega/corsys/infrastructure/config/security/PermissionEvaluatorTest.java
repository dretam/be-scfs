package bank_mega.corsys.infrastructure.config.security;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionCode;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.permission.PermissionName;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleIcon;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.user.UserName;
import bank_mega.corsys.domain.model.user.UserEmail;
import bank_mega.corsys.domain.model.user.UserPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PermissionEvaluator}.
 */
@DisplayName("PermissionEvaluator Tests")
class PermissionEvaluatorTest {

    @Test
    @DisplayName("Should return false when user is null")
    void shouldReturnFalseWhenUserIsNull() {
        boolean result = PermissionEvaluator.hasPermission(null, "USER_READ");
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when user has no role")
    void shouldReturnFalseWhenUserHasNoRole() {
        User user = createUserWithRole(null);
        boolean result = PermissionEvaluator.hasPermission(user, "USER_READ");
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true for ROLE_SU user with any permission")
    void shouldReturnTrueForSuperUser() {
        User suUser = createUserWithRole("ROLE_SU", Set.of("USER_READ"));
        assertTrue(PermissionEvaluator.hasPermission(suUser, "USER_CREATE"));
        assertTrue(PermissionEvaluator.hasPermission(suUser, "USER_READ"));
        assertTrue(PermissionEvaluator.hasPermission(suUser, "ANY_PERMISSION"));
    }

    @Test
    @DisplayName("Should return true when user has the required permission")
    void shouldReturnTrueWhenUserHasPermission() {
        User user = createUserWithRole("ROLE_ADMIN", Set.of("USER_READ", "USER_CREATE"));
        assertTrue(PermissionEvaluator.hasPermission(user, "USER_READ"));
        assertTrue(PermissionEvaluator.hasPermission(user, "USER_CREATE"));
    }

    @Test
    @DisplayName("Should return false when user does not have the required permission")
    void shouldReturnFalseWhenUserDoesNotHavePermission() {
        User user = createUserWithRole("ROLE_VIEW", Set.of("USER_READ"));
        assertFalse(PermissionEvaluator.hasPermission(user, "USER_CREATE"));
        assertFalse(PermissionEvaluator.hasPermission(user, "USER_DELETE"));
    }

    @Test
    @DisplayName("Should return true when user has any of the specified permissions")
    void shouldReturnTrueForHasAnyPermission() {
        User user = createUserWithRole("ROLE_ADMIN", Set.of("USER_READ", "USER_UPDATE"));

        assertTrue(PermissionEvaluator.hasAnyPermission(user, "USER_READ", "USER_CREATE"));
        assertTrue(PermissionEvaluator.hasAnyPermission(user, "USER_CREATE", "USER_READ"));
        assertTrue(PermissionEvaluator.hasAnyPermission(user, "USER_READ"));
    }

    @Test
    @DisplayName("Should return false when user has none of the specified permissions")
    void shouldReturnFalseForHasAnyPermission() {
        User user = createUserWithRole("ROLE_VIEW", Set.of("USER_READ"));

        assertFalse(PermissionEvaluator.hasAnyPermission(user, "USER_CREATE", "USER_DELETE"));
    }

    @Test
    @DisplayName("Should return true for ROLE_SU with hasAnyPermission")
    void shouldReturnTrueForSuperUserWithHasAnyPermission() {
        User suUser = createUserWithRole("ROLE_SU", Set.of());

        assertTrue(PermissionEvaluator.hasAnyPermission(suUser, "USER_CREATE", "USER_DELETE"));
    }

    @Test
    @DisplayName("Should return true when user has all of the specified permissions")
    void shouldReturnTrueForHasAllPermissions() {
        User user = createUserWithRole("ROLE_ADMIN", Set.of("USER_READ", "USER_CREATE", "USER_UPDATE"));

        assertTrue(PermissionEvaluator.hasAllPermissions(user, "USER_READ", "USER_CREATE"));
        assertTrue(PermissionEvaluator.hasAllPermissions(user, "USER_READ"));
    }

    @Test
    @DisplayName("Should return false when user does not have all of the specified permissions")
    void shouldReturnFalseForHasAllPermissions() {
        User user = createUserWithRole("ROLE_VIEW", Set.of("USER_READ"));

        assertFalse(PermissionEvaluator.hasAllPermissions(user, "USER_READ", "USER_CREATE"));
        assertFalse(PermissionEvaluator.hasAllPermissions(user, "USER_CREATE"));
    }

    @Test
    @DisplayName("Should return true for ROLE_SU with hasAllPermissions")
    void shouldReturnTrueForSuperUserWithHasAllPermissions() {
        User suUser = createUserWithRole("ROLE_SU", Set.of());

        assertTrue(PermissionEvaluator.hasAllPermissions(suUser, "USER_READ", "USER_CREATE", "USER_DELETE"));
    }

    @Test
    @DisplayName("Should return false for hasAnyPermission when user is null")
    void shouldReturnFalseForHasAnyPermissionWhenUserIsNull() {
        assertFalse(PermissionEvaluator.hasAnyPermission(null, "USER_READ"));
    }

    @Test
    @DisplayName("Should return false for hasAllPermissions when user is null")
    void shouldReturnFalseForHasAllPermissionsWhenUserIsNull() {
        assertFalse(PermissionEvaluator.hasAllPermissions(null, "USER_READ"));
    }

    // Helper methods

    private User createUserWithRole(String roleName, Set<String> permissionCodes) {
        Role role = createRole(roleName, permissionCodes);
        return createUserWithRole(role);
    }

    private User createUserWithRole(Role role) {
        return new User(
                new UserId(1L),
                new UserName("testuser"),
                new UserEmail("test@example.com"),
                new UserPassword("password123"),
                role,
                null, // type
                AuditTrail.create(0L)
        );
    }

    private Role createRole(String roleName, Set<String> permissionCodes) {
        Role role = new Role(
                new RoleId(1L),
                new RoleName(roleName),
                new RoleIcon("icon"),
                "Test Role",
                AuditTrail.create(0L)
        );

        // Add permissions to role
        long permissionId = 1L;
        for (String code : permissionCodes) {
            Permission permission = new Permission(
                    new PermissionId(permissionId++),
                    new PermissionName(formatPermissionName(code)),
                    new PermissionCode(code),
                    "Permission description",
                    AuditTrail.create(0L)
            );
            role.addPermission(permission);
        }

        return role;
    }

    private String formatPermissionName(String code) {
        return code.replace("_", " ");
    }

}
