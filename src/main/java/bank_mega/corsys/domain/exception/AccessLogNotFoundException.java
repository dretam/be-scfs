package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.activitylog.ActivityLogId;

public class AccessLogNotFoundException extends DomainException {

    public AccessLogNotFoundException(ActivityLogId accessLogId) {
        super("Access Log not found with id: " + accessLogId.value());
    }

}
