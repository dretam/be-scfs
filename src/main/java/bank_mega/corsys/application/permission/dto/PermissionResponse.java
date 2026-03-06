package bank_mega.corsys.application.permission.dto;

import lombok.Builder;

@Builder
public record PermissionResponse(
        Long id,
        String name,
        String code,
        String description,
        Long menuId
) {
}
