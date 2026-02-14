package bank_mega.corsys.application.auth.command;

import lombok.Builder;

@Builder
public record RefreshJWTCommand(
        String refreshToken
) {
}
