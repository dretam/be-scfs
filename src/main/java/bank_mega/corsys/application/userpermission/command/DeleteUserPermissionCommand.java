package bank_mega.corsys.application.userpermission.command;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Command to delete a user permission override.
 */
public record DeleteUserPermissionCommand(
        @NotNull(message = "userId is required")
        UUID userId,

        @NotNull(message = "permissionId is required")
        UUID permissionId
) {
}
