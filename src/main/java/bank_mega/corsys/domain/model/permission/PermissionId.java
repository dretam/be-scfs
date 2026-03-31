package bank_mega.corsys.domain.model.permission;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.util.UUID;

public record PermissionId(UUID value) {

    public PermissionId {
        if (value == null) {
            throw new DomainRuleViolationException("PermissionId must be filled");
        }
    }

    public static PermissionId of(UUID value) {
        return new PermissionId(value);
    }

}
