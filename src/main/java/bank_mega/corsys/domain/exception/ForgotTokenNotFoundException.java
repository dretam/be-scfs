package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.forgotpasstoken.ForgotTokenHash;

public class ForgotTokenNotFoundException extends DomainException {

    public ForgotTokenNotFoundException(ForgotTokenHash forgotTokenHash) {
        super("ForgotToken not found with id: " + forgotTokenHash.value());
    }

}
