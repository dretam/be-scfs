package bank_mega.corsys.domain.model.userpermission;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.util.UUID;

/**
 * Value object for UserPermission composite ID.
 */
public record UserPermissionId(UUID userId, UUID permissionId) {

    public UserPermissionId {
        if (userId == null) {
            throw new DomainRuleViolationException("UserPermissionId.userId must be filled");
        }
        if (permissionId == null) {
            throw new DomainRuleViolationException("UserPermissionId.permissionId must be positive");
        }
    }

}
