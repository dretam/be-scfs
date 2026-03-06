package bank_mega.corsys.infrastructure.config.security;

import bank_mega.corsys.domain.exception.AccessDeniedException;
import bank_mega.corsys.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Aspect for permission-based access control using @HasPermission annotation.
 * Evaluates if the authenticated user has the required permission before proceeding.
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class HasPermissionAspect {

    private final PermissionEvaluator permissionEvaluator;

    @Around("@annotation(HasPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        HasPermission hasPermission = method.getAnnotation(HasPermission.class);
        if (hasPermission == null) {
            return joinPoint.proceed();
        }

        String requiredPermission = hasPermission.value();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Authentication required for method: {}.{}", method.getDeclaringClass().getName(), method.getName());
            throw new SecurityException("Authentication required");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            log.warn("Invalid principal type for method: {}.{}. Principal: {}",
                    method.getDeclaringClass().getName(), method.getName(), principal.getClass().getName());
            throw new SecurityException("Invalid principal type");
        }

        // Check if user has the required permission
        if (!hasUserPermission(user, requiredPermission)) {
            log.warn("Access denied for user '{}' (role: {}) - Required permission: {}",
                    user.getEmail().value(),
                    user.getRole() != null ? user.getRole().getName().value() : "NONE",
                    requiredPermission);
            throw new AccessDeniedException(requiredPermission);
        }

        log.debug("Access granted for user '{}' (role: {}) - Permission: {}",
                user.getEmail().value(),
                user.getRole() != null ? user.getRole().getName().value() : "NONE",
                requiredPermission);

        return joinPoint.proceed();
    }

    private boolean hasUserPermission(User user, String requiredPermission) {
        // Use the PermissionEvaluator which includes user permission overrides
        return permissionEvaluator.hasPermission(user, requiredPermission);
    }

}
