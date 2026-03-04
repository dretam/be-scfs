package bank_mega.corsys.application.menu.command;

import lombok.Builder;

import java.util.Optional;

@Builder
public record UpdateMenuCommand(
        Long id,
        Optional<String> name,
        Optional<String> code,
        Optional<String> path,
        Optional<String> icon,
        Optional<Long> parentId,
        Optional<Integer> sortOrder
) {
}
