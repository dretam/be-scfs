package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.accesslog.AccessLogId;

public class AccessLogNotFoundException extends DomainException {

    public AccessLogNotFoundException(AccessLogId accessLogId) {
        super("Access Log not found with id: " + accessLogId.value());
    }

}
