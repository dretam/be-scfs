package bank_mega.corsys.domain.exception;

public class UserNameInvalidException extends DomainException {

    public UserNameInvalidException(String username) {
        super("Invalid username: " + username);
    }

}
