package bank_mega.corsys.application.permission.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PermissionResponse(
        UUID id,
        String name,
        String code,
        String description,
        UUID menuId
) {
}
