package bank_mega.corsys.domain.model.accesslog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record AccessLogId(Long value) {

    public AccessLogId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("AccessLogId value cannot be null");
        }
    }

    public static AccessLogId of(Long value) {
        return new AccessLogId(value);
    }

}
