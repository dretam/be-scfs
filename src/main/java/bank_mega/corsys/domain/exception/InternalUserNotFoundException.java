package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.internaluser.InternalUserName;

public class InternalUserNotFoundException extends DomainException {

    public InternalUserNotFoundException(InternalUserName userName) {
        super("InternalUser not found with userName: " + userName.value());
    }

}
