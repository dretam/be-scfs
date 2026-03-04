package bank_mega.corsys.application.permission.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record PermissionResponse(
        Long id,
        String name,
        String code,
        String description
) {
}
