package bank_mega.corsys.domain.model.activitylog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.util.UUID;

public record ActivityLogId(UUID value) {

    public ActivityLogId {
        if (value == null) {
            throw new DomainRuleViolationException("AccessLogId value cannot be null");
        }
    }

    public static ActivityLogId of(UUID value) {
        return new ActivityLogId(value);
    }

}
