package bank_mega.corsys.domain.model.token;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.util.UUID;

public record TokenId(UUID value) {

    public TokenId {
        if (value == null) {
            throw new DomainRuleViolationException("TokenId value cannot be null");
        }
    }

    public static TokenId of(UUID value) {
        return new TokenId(value);
    }

}
