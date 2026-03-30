package bank_mega.corsys.domain.model.userpermission;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

/**
 * Value object for UserPermission composite ID.
 */
public record UserPermissionId(String userId, Long permissionId) {

    public UserPermissionId {
        if (userId == null || userId.isBlank()) {
            throw new DomainRuleViolationException("UserPermissionId.userId must be filled");
        }
        if (permissionId == null || permissionId <= 0) {
            throw new DomainRuleViolationException("UserPermissionId.permissionId must be positive");
        }
    }

}
