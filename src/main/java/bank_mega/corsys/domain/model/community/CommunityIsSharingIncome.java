package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityIsSharingIncome(Boolean value) {
    public CommunityIsSharingIncome {
        if (value == null) {
            throw new DomainRuleViolationException("CommunityIsSharingIncome cannot be null");
        }
    }
}
