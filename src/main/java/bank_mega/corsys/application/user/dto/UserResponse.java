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
        Long id,
        String name,
        String email,
        RoleResponse role,
        UserDetailResponse userDetail,
        List<UserPermissionResponse> userPermissionOverride,
        Instant createdAt,
        Long createdBy,
        Instant updatedAt,
        Long updatedBy,
        Instant deletedAt,
        Long deletedBy
) {
}
