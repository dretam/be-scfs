package bank_mega.corsys.domain.exception;

/**
 * Exception thrown when a user attempts to access a resource without the required permission.
 */
public class AccessDeniedException extends DomainException {

    public AccessDeniedException(String requiredPermission) {
        super("Access denied. Required permission: " + requiredPermission);
    }

    public AccessDeniedException(String requiredPermission, Throwable cause) {
        super("Access denied. Required permission: " + requiredPermission, cause);
    }

}
