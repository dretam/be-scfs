package bank_mega.corsys.domain.model.role;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record RoleName(String value) {

    public RoleName {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("RoleName cannot be null or blank");
        }
    }

}
