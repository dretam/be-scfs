package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record UserId(Long value) {

    public UserId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("UserId value cannot be null");
        }
    }

    public static UserId of(Long value) {
        return new UserId(value);
    }

}
