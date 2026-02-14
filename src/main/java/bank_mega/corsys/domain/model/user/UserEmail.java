package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record UserEmail(String value) {

    public UserEmail {
        if (value == null || !value.contains("@")) {
            throw new DomainRuleViolationException("Invalid email address");
        }
    }

}
