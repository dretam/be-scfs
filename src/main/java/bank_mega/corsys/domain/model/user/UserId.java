package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.util.UUID;

public record UserId(UUID value) {

    public UserId {
        if (value == null) {
            throw new DomainRuleViolationException("UserId value cannot be null or blank");
        }
    }

    public static UserId of(UUID value) {
        return new UserId(value);
    }

}
