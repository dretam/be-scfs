package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityMinFinancing(Integer value) {
    public CommunityMinFinancing {
        if (value == null || value < 0) {
            throw new DomainRuleViolationException("CommunityMinFinancing cannot be null or negative");
        }
    }
}
