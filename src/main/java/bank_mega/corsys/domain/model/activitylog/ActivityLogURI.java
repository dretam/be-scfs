package bank_mega.corsys.domain.model.activitylog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ActivityLogURI(String value) {

    public ActivityLogURI {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("AccessLogURI value cannot be null");
        }
    }

}
