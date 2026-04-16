package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityMaxFinancing(Integer value) {
    public CommunityMaxFinancing {
        if (value == null || value < 0) {
            throw new DomainRuleViolationException("CommunityMaxFinancing cannot be null or negative");
        }
    }
}
