package bank_mega.corsys.domain.model.permission;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record PermissionName(String value) {

    public PermissionName {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("PermissionName cannot be null or blank");
        }
    }

}
