package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityIsFundReserve(Boolean value) {
    public CommunityIsFundReserve {
        if (value == null) {
            throw new DomainRuleViolationException("CommunityIsFundReserve cannot be null");
        }
    }
}
