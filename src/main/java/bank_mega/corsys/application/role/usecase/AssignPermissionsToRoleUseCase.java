package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.role.command.AssignPermissionsCommand;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.domain.exception.PermissionNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.PermissionRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class AssignPermissionsToRoleUseCase {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    public RoleResponse execute(AssignPermissionsCommand command, User authPrincipal) {
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleId(command.roleId()))
                .orElseThrow(() -> new RoleNotFoundException(new RoleId(command.roleId())));

        // Add permissions to role
        for (Long permissionId : command.permissionIds()) {
            Permission permission = permissionRepository.findFirstByIdAndAuditDeletedAtIsNull(
                            new PermissionId(permissionId))
                    .orElseThrow(() -> new PermissionNotFoundException(new PermissionId(permissionId)));
            role.addPermission(permission);
        }

        role.updateAudit(authPrincipal.getId().value());

        Role saved = roleRepository.save(role);

        return RoleAssembler.toResponse(saved);
    }

}
