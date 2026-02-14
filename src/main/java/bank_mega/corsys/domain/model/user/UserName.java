package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record UserName(String value) {

    public UserName {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("UserName value cannot be null or blank");
        }
    }

}
