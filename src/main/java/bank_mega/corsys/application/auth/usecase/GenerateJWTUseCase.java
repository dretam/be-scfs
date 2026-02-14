package bank_mega.corsys.application.auth.usecase;

import bank_mega.corsys.application.auth.command.GenerateJWTCommand;
import bank_mega.corsys.application.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.util.List;


@UseCase
@RequiredArgsConstructor
public class GenerateJWTUseCase {

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.audience}")
    private String jwtAudience;

    private final JwtEncoder jwtEncoder;

    public String execute(GenerateJWTCommand command) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .audience(List.of(jwtAudience))
                .issuedAt(Instant.now())
                .expiresAt(command.expiresAt())
                .subject(command.user().getName().value())
                .claim("uid", command.user().getId().value())
                .claim("role", command.user().getRole().getName().value())
                .build();
        return jwtEncoder.encode(
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS256).build(),
                        claims
                )
        ).getTokenValue();
    }

}
