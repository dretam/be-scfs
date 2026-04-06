package bank_mega.corsys.domain.exception;


public class UserComparePasswordException extends DomainException {

    public UserComparePasswordException() {
        super("Password & password confirmation not match");
    }

}
