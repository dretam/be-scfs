package bank_mega.corsys.application.user.dto;

import bank_mega.corsys.application.company.dto.CompanyResponse;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.application.rolechildren.dto.RoleChildrenResponse;
import bank_mega.corsys.application.userpermission.dto.UserPermissionResponse;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        String name,
        String fullName,
        String email,
        Boolean isActive,
        String photoPath,
        RoleResponse role,
        RoleChildrenResponse roleChildren,
        CompanyResponse company,
        List<UserPermissionResponse> userPermissionOverride,
        Instant createdAt,
        UUID createdBy,
        Instant updatedAt,
        UUID updatedBy,
        Instant deletedAt,
        UUID deletedBy
) {
}
