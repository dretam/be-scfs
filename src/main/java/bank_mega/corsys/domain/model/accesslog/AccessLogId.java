package bank_mega.corsys.domain.model.accesslog;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.util.UUID;

public record AccessLogId(UUID value) {

    public AccessLogId {
        if (value == null) {
            throw new DomainRuleViolationException("AccessLogId value cannot be null");
        }
    }

    public static AccessLogId of(UUID value) {
        return new AccessLogId(value);
    }

}
