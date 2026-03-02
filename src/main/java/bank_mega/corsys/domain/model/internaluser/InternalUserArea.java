package bank_mega.corsys.domain.model.internaluser;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record InternalUserArea(String value) {
    public InternalUserArea {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("InternalUserArea value cannot be null or blank");
        }
    }

    public static InternalUserArea of(String value) {
        return new InternalUserArea(value);
    }
}
