package bank_mega.corsys.application.auth.dto;

import lombok.Builder;

@Builder
public record LoginJWTResponse(
        Integer status,
        String message,
        String refreshToken,
        String accessToken
) {
}
