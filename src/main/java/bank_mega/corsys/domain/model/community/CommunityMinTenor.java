package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityMinTenor(Integer value) {
    public CommunityMinTenor {
        if (value == null || value < 0) {
            throw new DomainRuleViolationException("CommunityMinTenor cannot be null or negative");
        }
    }
}
