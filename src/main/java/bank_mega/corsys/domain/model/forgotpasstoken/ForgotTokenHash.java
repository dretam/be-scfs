package bank_mega.corsys.domain.model.forgotpasstoken;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ForgotTokenHash(String value) {

    public ForgotTokenHash {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ForgotTokenHash value cannot be null or blank");
        }
    }

}
