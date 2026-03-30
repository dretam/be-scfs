package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record UserId(String value) {

    public UserId {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("UserId value cannot be null or blank");
        }
    }

    public static UserId of(String value) {
        return new UserId(value);
    }

}
