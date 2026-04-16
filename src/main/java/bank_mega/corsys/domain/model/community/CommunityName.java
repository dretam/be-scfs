package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityName(String value) {
    public CommunityName {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("CommunityName cannot be null or blank");
        }
    }
}
