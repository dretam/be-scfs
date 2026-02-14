package bank_mega.corsys.domain.exception;


public class UserExistingPasswordException extends DomainException {

    public UserExistingPasswordException() {
        super("Existing password wrong");
    }

}
