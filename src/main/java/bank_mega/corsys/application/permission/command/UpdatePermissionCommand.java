package bank_mega.corsys.application.permission.command;

import lombok.Builder;

import java.util.Optional;

@Builder
public record UpdatePermissionCommand(
        Long id,
        Optional<String> name,
        Optional<String> code,
        Optional<String> description
) {
}
