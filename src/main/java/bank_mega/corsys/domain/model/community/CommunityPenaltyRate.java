package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityPenaltyRate(Integer value) {
    public CommunityPenaltyRate {
        if (value == null || value < 0) {
            throw new DomainRuleViolationException("CommunityPenaltyRate cannot be null or negative");
        }
    }
}
