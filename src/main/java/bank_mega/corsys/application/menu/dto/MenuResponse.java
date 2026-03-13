package bank_mega.corsys.application.menu.dto;

import bank_mega.corsys.application.permission.dto.PermissionResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record MenuResponse(
        Long id,
        String name,
        String code,
        String path,
        String icon,
        Long parentId,
        Integer sortOrder,
        List<PermissionResponse> permissions,
        List<MenuResponse> children
) {
}
