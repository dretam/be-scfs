package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityIsInterestReserve(Boolean value) {
    public CommunityIsInterestReserve {
        if (value == null) {
            throw new DomainRuleViolationException("CommunityIsInterestReserve cannot be null");
        }
    }
}
