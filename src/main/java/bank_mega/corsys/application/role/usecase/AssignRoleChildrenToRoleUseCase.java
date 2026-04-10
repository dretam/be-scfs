package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.role.command.AssignMenusCommand;
import bank_mega.corsys.application.role.command.AssignRoleChildrenCommand;
import bank_mega.corsys.application.role.dto.RoleResponse;
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
public class AssignRoleChildrenToRoleUseCase {

    private final RoleRepository roleRepository;
    private final RoleChildrenRepository roleChildrenRepository;

    @Transactional
    public RoleResponse execute(AssignRoleChildrenCommand command, User authPrincipal) {
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(command.roleId()))
                .orElseThrow(() -> new RoleNotFoundException(new RoleCode(command.roleId())));

        // Add menus to role
        for (String roleChildrenId : command.roleChildrenIds()) {
            RoleChildren roleChildren =
                    roleChildrenRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleChildrenCode(roleChildrenId))
                    .orElseThrow(() -> new RoleChildrenNotFoundException(new RoleChildrenCode(roleChildrenId)));
            role.addRoleChildren(roleChildren);
        }

        role.updateAudit(authPrincipal.getId().value());

        Role saved = roleRepository.save(role);

        return RoleAssembler.toResponse(saved);
    }

}
