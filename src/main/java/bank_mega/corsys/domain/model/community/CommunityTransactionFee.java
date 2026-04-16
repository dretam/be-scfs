package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.math.BigDecimal;

public record CommunityTransactionFee(BigDecimal value) {
    public CommunityTransactionFee {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainRuleViolationException("CommunityTransactionFee cannot be null or negative");
        }
    }
}
