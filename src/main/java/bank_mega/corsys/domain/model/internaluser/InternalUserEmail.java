package bank_mega.corsys.domain.model.internaluser;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record InternalUserEmail(String value) {
    public InternalUserEmail {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("InternalUserEmail value cannot be null or blank");
        }
    }

    public static InternalUserEmail of(String value) {
        return new InternalUserEmail(value);
    }
}
