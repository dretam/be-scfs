package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityIsActive(Boolean value) {
    public CommunityIsActive {
        if (value == null) {
            throw new DomainRuleViolationException("CommunityIsActive cannot be null");
        }
    }
}
