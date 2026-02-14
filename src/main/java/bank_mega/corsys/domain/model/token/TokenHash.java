package bank_mega.corsys.domain.model.token;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record TokenHash(String value) {

    public TokenHash {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("TokenHash value cannot be null or blank");
        }
    }

}
