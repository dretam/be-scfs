package bank_mega.corsys.domain.exception;

public class UserEmailInvalidException extends DomainException {

    public UserEmailInvalidException(String email) {
        super("Invalid email address: " + email);
    }

}
