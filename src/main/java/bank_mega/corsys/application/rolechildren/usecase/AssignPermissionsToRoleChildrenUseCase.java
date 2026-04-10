package bank_mega.corsys.application.rolechildren.usecase;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.assembler.RoleChildrenAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.role.command.AssignPermissionsCommand;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.application.rolechildren.dto.RoleChildrenResponse;
import bank_mega.corsys.domain.exception.PermissionNotFoundException;
import bank_mega.corsys.domain.exception.RoleChildrenNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.PermissionRepository;
import bank_mega.corsys.domain.repository.RoleChildrenRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class AssignPermissionsToRoleChildrenUseCase {

    private final RoleChildrenRepository roleChildrenRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    public RoleChildrenResponse execute(AssignPermissionsCommand command, User authPrincipal) {
        RoleChildren roleChildren =
                roleChildrenRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleChildrenCode(command.roleId()))
                .orElseThrow(() -> new RoleChildrenNotFoundException(new RoleChildrenCode(command.roleId())));

        // Add permissions to role
        for (UUID permissionId : command.permissionIds()) {
            Permission permission = permissionRepository.findFirstByIdAndAuditDeletedAtIsNull(
                            new PermissionId(permissionId))
                    .orElseThrow(() -> new PermissionNotFoundException(new PermissionId(permissionId)));
            roleChildren.addPermission(permission);
        }

        roleChildren.updateAudit(authPrincipal.getId().value());

        RoleChildren saved = roleChildrenRepository.save(roleChildren);

        return RoleChildrenAssembler.toResponse(saved);
    }

}
