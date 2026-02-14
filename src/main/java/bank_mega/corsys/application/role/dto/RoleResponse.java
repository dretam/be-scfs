package bank_mega.corsys.application.role.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record RoleResponse(
        Long id,
        String name,
        String icon,
        String description,
        Instant createdAt,
        Long createdBy,
        Instant updatedAt,
        Long updatedBy,
        Instant deletedAt,
        Long deletedBy
) {
}
