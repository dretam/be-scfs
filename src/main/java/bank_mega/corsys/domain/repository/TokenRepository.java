package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.token.Token;
import bank_mega.corsys.domain.model.token.TokenHash;
import bank_mega.corsys.domain.model.user.User;

import java.util.Optional;

public interface TokenRepository {

    Token save(Token token);

    void delete(Token token);

    long count();

    Optional<Token> findFirstValidByTokenHash(TokenHash tokenHash);

    String hashToken(String rawToken);

    void revokeAllTokenRefreshByUser(User user);

    void lastUsingTokenRefreshByTokenHash(TokenHash hash);

}
