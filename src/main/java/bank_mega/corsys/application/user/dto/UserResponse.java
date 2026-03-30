package bank_mega.corsys.application.user.dto;

import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.application.userpermission.dto.UserPermissionResponse;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Builder
public record UserResponse(
        String id,
        String name,
        String email,
        RoleResponse role,
        List<UserPermissionResponse> userPermissionOverride,
        Instant createdAt,
        String createdBy,
        Instant updatedAt,
        String updatedBy,
        Instant deletedAt,
        String deletedBy
) {
}
