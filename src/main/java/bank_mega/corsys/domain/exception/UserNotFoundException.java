package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.user.UserId;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException(UserId userId) {
        super("User not found with id: " + userId.value());
    }

}
