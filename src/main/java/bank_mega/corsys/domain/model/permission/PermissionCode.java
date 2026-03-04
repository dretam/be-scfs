package bank_mega.corsys.domain.model.permission;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record PermissionCode(String value) {

    public PermissionCode {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("PermissionCode cannot be null or blank");
        }
        if (!value.matches("^[A-Z_]+$")) {
            throw new DomainRuleViolationException(
                    "PermissionCode must be uppercase with underscores (e.g., USER_CREATE)"
            );
        }
    }

}
