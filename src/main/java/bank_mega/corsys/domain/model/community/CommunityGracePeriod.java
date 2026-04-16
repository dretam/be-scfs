package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityGracePeriod(Integer value) {
    public CommunityGracePeriod {
        if (value == null || value < 0) {
            throw new DomainRuleViolationException("CommunityGracePeriod cannot be null or negative");
        }
    }
}
