package bank_mega.corsys.domain.model.role;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record RoleIcon(String value) {

    public RoleIcon {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("RoleIcon cannot be null or blank");
        }
    }

}
