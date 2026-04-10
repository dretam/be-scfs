package bank_mega.corsys.application.rolechildren.dto;

import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.application.permission.dto.PermissionResponse;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record RoleChildrenResponse(
        String id,
        String name,
        String icon,
        String description,
        Instant createdAt,
        UUID createdBy,
        Instant updatedAt,
        UUID updatedBy,
        Instant deletedAt,
        UUID deletedBy,
        List<PermissionResponse> permissions,
        List<MenuResponse> menus
) {
}
