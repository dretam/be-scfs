package bank_mega.corsys.domain.model.accesslog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record AccessLogIpAddress(String value) {

    public AccessLogIpAddress {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("AccessLogIpAddress value cannot be null");
        }
    }

}
