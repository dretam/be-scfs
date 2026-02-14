package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.token.TokenHash;

public class TokenNotFoundException extends DomainException {

    public TokenNotFoundException(TokenHash tokenHash) {
        super("Token not found with id: " + tokenHash.value());
    }

}
