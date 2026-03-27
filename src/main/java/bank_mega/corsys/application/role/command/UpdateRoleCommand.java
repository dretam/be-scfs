package bank_mega.corsys.application.role.command;

import lombok.Builder;

import java.util.Optional;

@Builder
public record UpdateRoleCommand(
        String id,
        Optional<String> name,
        Optional<String> icon,
        Optional<String> description
) {
}
