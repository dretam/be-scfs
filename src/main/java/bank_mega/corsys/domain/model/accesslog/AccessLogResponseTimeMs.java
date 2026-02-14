package bank_mega.corsys.domain.model.accesslog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record AccessLogResponseTimeMs(Long value) {

    public AccessLogResponseTimeMs {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("AccessLogResponseTimeMs value cannot be null");
        }
    }

    public static AccessLogResponseTimeMs of(Long value) {
        return new AccessLogResponseTimeMs(value);
    }

}
