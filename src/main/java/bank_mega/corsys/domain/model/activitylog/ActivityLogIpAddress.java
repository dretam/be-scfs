package bank_mega.corsys.domain.model.activitylog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ActivityLogIpAddress(String value) {

    public ActivityLogIpAddress {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("AccessLogIpAddress value cannot be null");
        }
    }

}
