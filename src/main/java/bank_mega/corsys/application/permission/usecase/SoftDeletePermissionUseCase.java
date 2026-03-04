package bank_mega.corsys.application.permission.usecase;

import bank_mega.corsys.application.assembler.PermissionAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.permission.command.SoftDeletePermissionCommand;
import bank_mega.corsys.application.permission.dto.PermissionResponse;
import bank_mega.corsys.domain.exception.PermissionNotFoundException;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeletePermissionUseCase {

    private final PermissionRepository permissionRepository;

    @Transactional
    public PermissionResponse execute(SoftDeletePermissionCommand command, User authPrincipal) {
        Permission permission = permissionRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new PermissionId(command.id()))
                .orElseThrow(() -> new PermissionNotFoundException(new PermissionId(command.id())));

        permission.deleteAudit(authPrincipal.getId().value());

        Permission deleted = permissionRepository.save(permission);

        return PermissionAssembler.toResponse(deleted);
    }

}
