package bank_mega.corsys.domain.model.activitylog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ActivityLogStatusCode(Integer value) {

    public ActivityLogStatusCode {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("AccessLogStatusCode value cannot be null");
        }
    }

    public static ActivityLogStatusCode of(Integer value) {
        return new ActivityLogStatusCode(value);
    }

}
