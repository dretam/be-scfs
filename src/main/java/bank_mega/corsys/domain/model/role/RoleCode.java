package bank_mega.corsys.domain.model.role;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record RoleCode(String value) {

    public RoleCode {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("RoleCode cannot be null or blank");
        }
        if (!value.matches("^[A-Z_]+$")) {
            throw new DomainRuleViolationException(
                    "RoleCode must be uppercase with underscores (e.g., ROLE_ADMIN)"
            );
        }
    }

}
