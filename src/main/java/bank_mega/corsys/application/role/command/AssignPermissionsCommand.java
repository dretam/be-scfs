package bank_mega.corsys.application.role.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@Builder
public record AssignPermissionsCommand(
        @NotNull Long roleId,
        @NotEmpty Set<Long> permissionIds
) {
}
