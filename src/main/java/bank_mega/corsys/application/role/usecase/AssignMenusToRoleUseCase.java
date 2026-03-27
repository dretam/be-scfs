package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.role.command.AssignMenusCommand;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.domain.exception.MenuNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.MenuRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class AssignMenusToRoleUseCase {

    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public RoleResponse execute(AssignMenusCommand command, User authPrincipal) {
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(command.roleId()))
                .orElseThrow(() -> new RoleNotFoundException(new RoleCode(command.roleId())));

        // Add menus to role
        for (Long menuId : command.menuIds()) {
            Menu menu = menuRepository.findFirstByIdAndAuditDeletedAtIsNull(new MenuId(menuId))
                    .orElseThrow(() -> new MenuNotFoundException(new MenuId(menuId)));
            role.addMenu(menu);
        }

        role.updateAudit(authPrincipal.getId().value());

        Role saved = roleRepository.save(role);

        return RoleAssembler.toResponse(saved);
    }

}
