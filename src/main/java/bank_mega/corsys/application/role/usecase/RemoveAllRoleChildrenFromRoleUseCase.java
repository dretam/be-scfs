package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RemoveAllRoleChildrenFromRoleUseCase {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleResponse execute(String roleId, User authPrincipal) {
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(roleId))
                .orElseThrow(() -> new RoleNotFoundException(new RoleCode(roleId)));

        // Clear all menus
        role.getRoleChildren().clear();

        role.updateAudit(authPrincipal.getId().value());

        Role saved = roleRepository.save(role);

        return RoleAssembler.toResponse(saved);
    }

}
