package bank_mega.corsys.domain.model.accesslog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record AccessLogUserAgent(String value) {

    public AccessLogUserAgent {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("AccessLogUserAgent value cannot be null");
        }
    }

}
