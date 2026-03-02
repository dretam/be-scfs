package bank_mega.corsys.application.auth.usecase;

import bank_mega.corsys.application.auth.command.GenerateJWTCommand;
import bank_mega.corsys.application.auth.command.GenerateTokenCommand;
import bank_mega.corsys.application.auth.command.LoginJWTCommand;
import bank_mega.corsys.application.auth.dto.LoginJWTResponse;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.ldap.LDAPResponse;
import bank_mega.corsys.domain.model.token.TokenHash;
import bank_mega.corsys.domain.model.token.TokenType;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserEmail;
import bank_mega.corsys.domain.model.user.UserName;
import bank_mega.corsys.domain.port.LDAPService;
import bank_mega.corsys.domain.repository.TokenRepository;
import bank_mega.corsys.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Slf4j
@UseCase
@RequiredArgsConstructor
public class LoginJWTUseCase {

    @Value("${jwt.expires-seconds}")
    private Long jwtExpiresSeconds;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final GenerateTokenUseCase generateTokenUseCase;
    private final GenerateJWTUseCase generateJWTUseCase;
    private final LDAPService ldapService;

    @Transactional
    public LoginJWTResponse execute(LoginJWTCommand command) {

        LDAPResponse ldapResponse = ldapService.verifyPassword(command.username(), command.password());

        // 1. Check username atau email
        User user = userRepository
                .findFirstByNameAndAuditDeletedAtIsNull(new UserName(command.username()))
                .orElse(null);
        if (user == null) {
            user = userRepository
                    .findFirstByEmailAndAuditDeletedAtIsNull(new UserEmail(command.username()))
                    .orElseThrow(() -> new AuthorizationServiceException("Invalid username or password"));
        }

        // 2. Validasi password
        boolean checkPassword = BCrypt.checkpw(command.password(), user.getPassword().value());
        if (!checkPassword) {
            throw new AuthorizationServiceException("Invalid username or password");
        }

        // 3. Revoke token pengguna terkait apabila ada yang aktif
        tokenRepository.revokeAllTokenRefreshByUser(user);

        // 4. Generate refresh dan JWT Token
        Instant refreshTokenExpiresAt = Instant.now().plus(command.rememberMe() ? 30 : 1, ChronoUnit.DAYS);
        String refreshToken = generateTokenUseCase.execute(
                GenerateTokenCommand.builder()
                        .type(TokenType.REFRESH)
                        .expiresAt(refreshTokenExpiresAt)
                        .user(user)
                        .build()
        );
        String accessToken = generateJWTUseCase.execute(GenerateJWTCommand.builder()
                .user(user)
                .expiresAt(Instant.now().plusSeconds(jwtExpiresSeconds))
                .build());

        // 5. Catat penggunaan refresh token
        tokenRepository.lastUsingTokenRefreshByTokenHash(
                new TokenHash(
                        tokenRepository.hashToken(refreshToken)
                )
        );

        return LoginJWTResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
