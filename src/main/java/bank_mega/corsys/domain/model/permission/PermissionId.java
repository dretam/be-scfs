package bank_mega.corsys.domain.model.permission;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record PermissionId(Long value) {

    public PermissionId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("PermissionId must be a positive number");
        }
    }

    public static PermissionId of(Long value) {
        return new PermissionId(value);
    }

}
