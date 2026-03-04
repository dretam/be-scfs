package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.permission.PermissionId;

public class PermissionNotFoundException extends RuntimeException {

    public PermissionNotFoundException(PermissionId id) {
        super("Permission not found with id: " + id.value());
    }

    public PermissionNotFoundException(String message) {
        super(message);
    }

}
