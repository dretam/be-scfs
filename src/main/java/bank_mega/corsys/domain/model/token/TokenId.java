package bank_mega.corsys.domain.model.token;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record TokenId(Long value) {

    public TokenId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("TokenId value cannot be null");
        }
    }

    public static TokenId of(Long value) {
        return new TokenId(value);
    }

}
