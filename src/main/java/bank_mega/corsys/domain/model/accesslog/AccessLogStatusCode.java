package bank_mega.corsys.domain.model.accesslog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record AccessLogStatusCode(Integer value) {

    public AccessLogStatusCode {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("AccessLogStatusCode value cannot be null");
        }
    }

    public static AccessLogStatusCode of(Integer value) {
        return new AccessLogStatusCode(value);
    }

}
