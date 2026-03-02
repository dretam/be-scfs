package bank_mega.corsys.domain.exception;

public class UserAlreadyExistsException extends DomainException {

    public UserAlreadyExistsException(String username) {
        super("User already Exist : " + username);
    }

}
