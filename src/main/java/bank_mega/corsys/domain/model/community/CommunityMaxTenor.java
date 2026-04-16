package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityMaxTenor(Integer value) {
    public CommunityMaxTenor {
        if (value == null || value < 0) {
            throw new DomainRuleViolationException("CommunityMaxTenor cannot be null or negative");
        }
    }
}
