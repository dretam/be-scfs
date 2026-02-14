package bank_mega.corsys.application.auth.command;

import bank_mega.corsys.domain.model.user.User;
import lombok.Builder;

import java.time.Instant;

@Builder
public record GenerateJWTCommand(
        User user,
        Instant expiresAt
) {
}
