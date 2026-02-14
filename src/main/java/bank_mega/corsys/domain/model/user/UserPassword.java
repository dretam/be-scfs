package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record UserPassword(String value) {

    public UserPassword {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("UserPassword value cannot be null or blank");
        }
    }

}
