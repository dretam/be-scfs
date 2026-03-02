package bank_mega.corsys.domain.model.internaluser;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record InternalUserCount(Integer value) {
    public InternalUserCount {
        if (value == null) {
            throw new DomainRuleViolationException("InternalUserCount value cannot be null");
        }
    }

    public static InternalUserCount of(Integer value) {
        return new InternalUserCount(value);
    }
}
