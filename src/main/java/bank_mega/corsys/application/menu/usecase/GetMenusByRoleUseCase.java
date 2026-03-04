package bank_mega.corsys.application.menu.usecase;

import bank_mega.corsys.application.assembler.MenuAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetMenusByRoleUseCase {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public List<MenuResponse> execute(Long roleId) {
        List<Menu> menus = menuRepository.findAllByRoleId(new RoleId(roleId));

        return menus.stream()
                .map(MenuAssembler::toResponse)
                .toList();
    }

}
