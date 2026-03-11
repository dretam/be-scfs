package bank_mega.corsys.domain.model.userdetail;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record UserDetailId(Long value) {

    public UserDetailId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("UserDetailId value cannot be null");
        }
    }

    public static UserDetailId of(Long value) {
        return new UserDetailId(value);
    }

}
