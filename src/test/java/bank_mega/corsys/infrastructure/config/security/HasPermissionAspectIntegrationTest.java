package bank_mega.corsys.infrastructure.config.security;

import bank_mega.corsys.domain.exception.AccessDeniedException;
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
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Integration tests for {@link HasPermissionAspect}.
 */
@DisplayName("HasPermissionAspect Integration Tests")
@ExtendWith(MockitoExtension.class)
class HasPermissionAspectIntegrationTest {

    private HasPermissionAspect aspect;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @BeforeEach
    void setUp() {
        aspect = new HasPermissionAspect();
        SecurityContextHolder.clearContext();
        lenient().when(joinPoint.getSignature()).thenReturn(methodSignature);
    }

    @Test
    @DisplayName("Should proceed when user has required permission")
    void shouldProceedWhenUserHasPermission() throws Throwable {
        // Arrange
        User user = createUserWithRole("ROLE_ADMIN", Set.of("USER_READ", "USER_CREATE"));
        setAuthentication(user);

        Method method = TestService.class.getMethod("methodWithReadPermission");
        when(methodSignature.getMethod()).thenReturn(method);
        when(joinPoint.proceed()).thenReturn("Success");

        // Act
        Object result = aspect.checkPermission(joinPoint);

        // Assert
        assertEquals("Success", result);
        verify(joinPoint).proceed();
    }

    @Test
    @DisplayName("Should throw AccessDeniedException when user lacks permission")
    void shouldThrowAccessDeniedExceptionWhenUserLacksPermission() throws Throwable {
        // Arrange
        User user = createUserWithRole("ROLE_VIEW", Set.of("USER_READ"));
        setAuthentication(user);

        Method method = TestService.class.getMethod("methodWithCreatePermission");
        when(methodSignature.getMethod()).thenReturn(method);

        // Act & Assert
        assertThrows(
                AccessDeniedException.class,
                () -> aspect.checkPermission(joinPoint)
        );
        verify(joinPoint, never()).proceed();
    }

    @Test
    @DisplayName("Should throw SecurityException when not authenticated")
    void shouldThrowSecurityExceptionWhenNotAuthenticated() throws Throwable {
        // Arrange
        SecurityContextHolder.clearContext();

        Method method = TestService.class.getMethod("methodWithReadPermission");
        when(methodSignature.getMethod()).thenReturn(method);

        // Act & Assert
        assertThrows(
                SecurityException.class,
                () -> aspect.checkPermission(joinPoint)
        );
        verify(joinPoint, never()).proceed();
    }

    @Test
    @DisplayName("Should allow super user to access any permission")
    void shouldAllowSuperUserAnyAccess() throws Throwable {
        // Arrange
        User suUser = createUserWithRole("ROLE_SU", Set.of());
        setAuthentication(suUser);

        Method method = TestService.class.getMethod("methodWithCreatePermission");
        when(methodSignature.getMethod()).thenReturn(method);
        when(joinPoint.proceed()).thenReturn("Success");

        // Act
        Object result = aspect.checkPermission(joinPoint);

        // Assert
        assertEquals("Success", result);
        verify(joinPoint).proceed();
    }

    // Helper methods

    private void setAuthentication(User user) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, java.util.Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private User createUserWithRole(String roleName, Set<String> permissionCodes) {
        Role role = createRole(roleName, permissionCodes);
        return new User(
                new UserId(1L),
                new UserName("testuser"),
                new UserEmail("test@example.com"),
                new UserPassword("password123"),
                role,
                null,
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

    // Test service class

    static class TestService {

        @HasPermission("USER_READ")
        public String methodWithReadPermission() {
            return "Success";
        }

        @HasPermission("USER_CREATE")
        public String methodWithCreatePermission() {
            return "Success";
        }
    }

}
