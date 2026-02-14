package bank_mega.corsys.application.auth.dto;

import lombok.Builder;

@Builder
public record RefreshJWTResponse(
        Integer status,
        String message,
        String accessToken
) {
}
