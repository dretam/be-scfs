package bank_mega.corsys.application.menu.command;

import lombok.Builder;

import java.util.Optional;
import java.util.UUID;

@Builder
public record UpdateMenuCommand(
        UUID id,
        Optional<String> name,
        Optional<String> code,
        Optional<String> path,
        Optional<String> icon,
        Optional<UUID> parentId,
        Optional<Integer> sortOrder
) {
}
