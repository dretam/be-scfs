package bank_mega.corsys.application.permission.command;

import lombok.Builder;

import java.util.Optional;
import java.util.UUID;

@Builder
public record UpdatePermissionCommand(
        UUID id,
        Optional<String> name,
        Optional<String> code,
        Optional<String> description,
        Optional<UUID> menuId
) {
}
