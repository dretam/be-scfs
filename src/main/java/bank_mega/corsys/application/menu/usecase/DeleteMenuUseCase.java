package bank_mega.corsys.application.menu.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.MenuNotFoundException;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteMenuUseCase {

    private final MenuRepository menuRepository;

    @Transactional
    public MenuId execute(Long id) {
        MenuId menuId = new MenuId(id);

        Menu menu = menuRepository.findFirstById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(menuId));

        menuRepository.delete(menu);

        return menuId;
    }

}
