package bank_mega.corsys.domain.model.forgotpasstoken;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.util.UUID;

public record ForgotTokenId(UUID value) {

    public ForgotTokenId {
        if (value == null) {
            throw new DomainRuleViolationException("ForgotTokenId value cannot be null");
        }
    }

    public static ForgotTokenId of(UUID value) {
        return new ForgotTokenId(value);
    }

}