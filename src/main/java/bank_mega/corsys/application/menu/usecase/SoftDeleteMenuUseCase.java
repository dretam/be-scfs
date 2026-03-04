package bank_mega.corsys.application.menu.usecase;

import bank_mega.corsys.application.assembler.MenuAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.menu.command.SoftDeleteMenuCommand;
import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.domain.exception.MenuNotFoundException;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteMenuUseCase {

    private final MenuRepository menuRepository;

    @Transactional
    public MenuResponse execute(SoftDeleteMenuCommand command, User authPrincipal) {
        Menu menu = menuRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new MenuId(command.id()))
                .orElseThrow(() -> new MenuNotFoundException(new MenuId(command.id())));

        menu.deleteAudit(authPrincipal.getId().value());

        Menu deleted = menuRepository.save(menu);

        return MenuAssembler.toResponse(deleted);
    }

}
