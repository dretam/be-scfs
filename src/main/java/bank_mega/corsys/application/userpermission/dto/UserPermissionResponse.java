package bank_mega.corsys.application.userpermission.dto;

import lombok.Builder;

/**
 * DTO for User Permission Override response.
 */
@Builder
public record UserPermissionResponse(
        Long userId,
        Long permissionId,
        String permissionCode,
        String permissionName,
        String effect
) {
}
