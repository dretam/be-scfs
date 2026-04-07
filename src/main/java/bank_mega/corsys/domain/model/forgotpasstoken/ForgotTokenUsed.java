package bank_mega.corsys.domain.model.forgotpasstoken;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ForgotTokenUsed(Boolean value) {

    public ForgotTokenUsed {
        if (value == null) {
            throw new DomainRuleViolationException("ForgotTokenUsed value cannot be null");
        }
    }

}
