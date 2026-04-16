package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityIsExtendFinancing(Boolean value) {
    public CommunityIsExtendFinancing {
        if (value == null) {
            throw new DomainRuleViolationException("CommunityIsExtendFinancing cannot be null");
        }
    }
}
