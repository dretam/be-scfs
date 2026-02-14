package bank_mega.corsys.domain.model.accesslog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record AccessLogURI(String value) {

    public AccessLogURI {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("AccessLogURI value cannot be null");
        }
    }

}
