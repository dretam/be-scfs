package bank_mega.corsys.application.userpermission.dto;

import lombok.Builder;

import java.util.UUID;

/**
 * DTO for User Permission Override response.
 */
@Builder
public record UserPermissionResponse(
        UUID userId,
        UUID permissionId,
        String permissionCode,
        String permissionName,
        String effect
) {
}
