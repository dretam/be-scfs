package bank_mega.corsys.application.menu.dto;

import bank_mega.corsys.application.permission.dto.PermissionResponse;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record MenuResponse(
        UUID id,
        String name,
        String code,
        String path,
        String icon,
        UUID parentId,
        Integer sortOrder,
        List<PermissionResponse> permissions,
        List<MenuResponse> children
) {
}
