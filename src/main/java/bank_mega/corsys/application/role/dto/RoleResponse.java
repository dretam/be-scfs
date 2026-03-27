package bank_mega.corsys.application.role.dto;

import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.application.permission.dto.PermissionResponse;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record RoleResponse(
        String id,
        String name,
        String icon,
        String description,
        Instant createdAt,
        Long createdBy,
        Instant updatedAt,
        Long updatedBy,
        Instant deletedAt,
        Long deletedBy,
        List<PermissionResponse> permissions,
        List<MenuResponse> menus
) {
}
