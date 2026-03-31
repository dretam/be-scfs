package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record UserFullName(String value) {
    public UserFullName {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("UserFullName cannot be null or blank");
        }
    }
}
