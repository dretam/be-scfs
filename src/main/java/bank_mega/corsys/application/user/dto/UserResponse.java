package bank_mega.corsys.application.user.dto;

import bank_mega.corsys.application.role.dto.RoleResponse;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserResponse(
        Long id,
        String name,
        String email,
        RoleResponse role,
        Instant createdAt,
        Long createdBy,
        Instant updatedAt,
        Long updatedBy,
        Instant deletedAt,
        Long deletedBy
) {
}
