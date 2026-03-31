package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record UserIsActive(Boolean value) {
    public UserIsActive {
        if (value == null) {
            throw new DomainRuleViolationException("UserIsActive cannot be null");
        }
    }
}
