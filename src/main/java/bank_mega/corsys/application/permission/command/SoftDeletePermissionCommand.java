package bank_mega.corsys.application.permission.command;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SoftDeletePermissionCommand(
        UUID id
) {
}
