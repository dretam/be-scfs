package bank_mega.corsys.application.auth.usecase;

import bank_mega.corsys.application.auth.command.GenerateJWTCommand;
import bank_mega.corsys.application.auth.command.RefreshJWTCommand;
import bank_mega.corsys.application.auth.dto.RefreshJWTResponse;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.token.Token;
import bank_mega.corsys.domain.model.token.TokenHash;
import bank_mega.corsys.domain.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


@UseCase
@RequiredArgsConstructor
public class RefreshJWTUseCase {

    private final TokenRepository tokenRepository;
    private final GenerateJWTUseCase generateJWTUseCase;

    @Transactional
    public RefreshJWTResponse execute(RefreshJWTCommand command) {
        TokenHash hash = new TokenHash(tokenRepository.hashToken(command.refreshToken()));

        // 1. Check refresh token yang ada
        Token token = tokenRepository.findFirstValidByTokenHash(hash).orElse(null);
        if (token == null) {
            throw new AuthorizationServiceException("Please login again");
        }

        // 2. Generate JWT Token baru
        String accessToken = generateJWTUseCase.execute(GenerateJWTCommand.builder()
                .user(token.getUser())
                .expiresAt(Instant.now().plusSeconds(10L))
                .build());

        // 3. Catat penggunaan terakhir refresh token
        tokenRepository.lastUsingTokenRefreshByTokenHash(hash);

        return RefreshJWTResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .accessToken(accessToken)
                .build();
    }

}
