package bank_mega.corsys.application.auth.usecase;

import bank_mega.corsys.application.auth.command.GenerateTokenCommand;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.token.Token;
import bank_mega.corsys.domain.model.token.TokenHash;
import bank_mega.corsys.domain.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;


@UseCase
@RequiredArgsConstructor
public class GenerateTokenUseCase {

    private static final SecureRandom RANDOM = new SecureRandom();
    private final TokenRepository tokenRepository;

    @Transactional
    public String execute(GenerateTokenCommand command) {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);

        String rawToken = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        Token token = new Token(
                null,
                command.user(),
                new TokenHash(tokenRepository.hashToken(rawToken)),
                command.type(),
                command.expiresAt(),
                null,
                null
        );
        tokenRepository.save(token);

        return rawToken;
    }

}
