package bank_mega.corsys.application.menu.usecase;

import bank_mega.corsys.application.assembler.MenuAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.domain.exception.MenuNotFoundException;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveMenuUseCase {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public MenuResponse execute(Long id) {
        Menu menu = menuRepository.findFirstByIdAndAuditDeletedAtIsNull(new MenuId(id))
                .orElseThrow(() -> new MenuNotFoundException(new MenuId(id)));

        return MenuAssembler.toResponse(menu);
    }

}
