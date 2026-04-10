package bank_mega.corsys.application.rolechildren.usecase;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.assembler.RoleChildrenAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.role.command.AssignMenusCommand;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.application.rolechildren.dto.RoleChildrenResponse;
import bank_mega.corsys.domain.exception.MenuNotFoundException;
import bank_mega.corsys.domain.exception.RoleChildrenNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.MenuRepository;
import bank_mega.corsys.domain.repository.RoleChildrenRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class AssignMenusToRoleChildrenUseCase {

    private final RoleChildrenRepository roleChildrenRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public RoleChildrenResponse execute(AssignMenusCommand command, User authPrincipal) {
        RoleChildren roleChildren =
                roleChildrenRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleChildrenCode(command.roleId()))
                .orElseThrow(() -> new RoleChildrenNotFoundException(new RoleChildrenCode(command.roleId())));

        // Add menus to role
        for (UUID menuId : command.menuIds()) {
            Menu menu = menuRepository.findFirstByIdAndAuditDeletedAtIsNull(new MenuId(menuId))
                    .orElseThrow(() -> new MenuNotFoundException(new MenuId(menuId)));
            roleChildren.addMenu(menu);
        }

        roleChildren.updateAudit(authPrincipal.getId().value());

        RoleChildren saved = roleChildrenRepository.save(roleChildren);

        return RoleChildrenAssembler.toResponse(saved);
    }

}
