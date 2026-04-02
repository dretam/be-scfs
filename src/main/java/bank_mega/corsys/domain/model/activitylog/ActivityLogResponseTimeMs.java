package bank_mega.corsys.domain.model.activitylog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ActivityLogResponseTimeMs(Long value) {

    public ActivityLogResponseTimeMs {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("AccessLogResponseTimeMs value cannot be null");
        }
    }

    public static ActivityLogResponseTimeMs of(Long value) {
        return new ActivityLogResponseTimeMs(value);
    }

}
