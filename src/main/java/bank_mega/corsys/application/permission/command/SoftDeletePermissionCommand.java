package bank_mega.corsys.application.permission.command;

import lombok.Builder;

@Builder
public record SoftDeletePermissionCommand(
        Long id
) {
}
