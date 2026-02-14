package bank_mega.corsys.application.auth.command;

import lombok.Builder;

@Builder
public record LoginJWTCommand(
        String username,
        String password,
        Boolean rememberMe
) {
}
