package bank_mega.corsys.application.menu.dto;

import bank_mega.corsys.application.permission.dto.PermissionResponse;
import bank_mega.corsys.domain.model.permission.Permission;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Set;

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
