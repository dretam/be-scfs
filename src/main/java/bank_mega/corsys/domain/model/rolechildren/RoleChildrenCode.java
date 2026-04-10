package bank_mega.corsys.domain.model.rolechildren;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record RoleChildrenCode(String value) {

    public RoleChildrenCode {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("RoleChildrenCode cannot be null or blank");
        }
        if (!value.matches("^[A-Z_]+$")) {
            throw new DomainRuleViolationException(
                    "RoleChildrenCode must be uppercase with underscores (e.g., ROLE_ADMIN)"
            );
        }
    }

}
