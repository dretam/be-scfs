package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CommunityEarlyPaymentFeeAmount(Long value) {
    public CommunityEarlyPaymentFeeAmount {
        if (value == null || value < 0) {
            throw new DomainRuleViolationException("CommunityEarlyPaymentFeeAmount cannot be null or negative");
        }
    }
}
