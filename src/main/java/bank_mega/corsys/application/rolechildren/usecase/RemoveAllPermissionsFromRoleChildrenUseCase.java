package bank_mega.corsys.application.rolechildren.usecase;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.assembler.RoleChildrenAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.application.rolechildren.dto.RoleChildrenResponse;
import bank_mega.corsys.domain.exception.RoleChildrenNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.RoleChildrenRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RemoveAllPermissionsFromRoleChildrenUseCase {

    private final RoleChildrenRepository roleChildrenRepository;

    @Transactional
    public RoleChildrenResponse execute(String roleChildrenId, User authPrincipal) {
        RoleChildren roleChildren =
                roleChildrenRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleChildrenCode(roleChildrenId))
                .orElseThrow(() -> new RoleChildrenNotFoundException(new RoleChildrenCode(roleChildrenId)));

        // Clear all permissions
        roleChildren.getPermissions().clear();

        roleChildren.updateAudit(authPrincipal.getId().value());

        RoleChildren saved = roleChildrenRepository.save(roleChildren);

        return RoleChildrenAssembler.toResponse(saved);
    }

}
