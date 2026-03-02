package bank_mega.corsys.domain.model.internaluser;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record InternalUserName(String value) {
    public InternalUserName {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("InternalUserName value cannot be null or blank");
        }
    }

    public static InternalUserName of(String value) {
        return new InternalUserName(value);
    }
}
