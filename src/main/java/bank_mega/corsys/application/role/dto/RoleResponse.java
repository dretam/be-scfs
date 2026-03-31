package bank_mega.corsys.application.role.dto;

import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.application.permission.dto.PermissionResponse;
import lombok.Builder;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record RoleResponse(
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
