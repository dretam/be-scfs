package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.forgotpasstoken.ForgotToken;
import bank_mega.corsys.domain.model.forgotpasstoken.ForgotTokenHash;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ForgotTokenRepository {
    ForgotToken save(ForgotToken forgotToken);

    void delete(ForgotToken forgotToken);

    long count();

    Optional<ForgotToken> findFirstValidByForgotTokenHash(ForgotTokenHash forgotTokenHash);

    String hashToken(String rawToken);
}
