package bank_mega.corsys.domain.model.activitylog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ActivityLogUserAgent(String value) {

    public ActivityLogUserAgent {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("AccessLogUserAgent value cannot be null");
        }
    }

}
