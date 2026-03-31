package bank_mega.corsys.application.permission.usecase;

import bank_mega.corsys.application.assembler.PermissionAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.permission.dto.PermissionResponse;
import bank_mega.corsys.domain.exception.PermissionNotFoundException;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class RetrievePermissionUseCase {

    private final PermissionRepository permissionRepository;

    @Transactional(readOnly = true)
    public PermissionResponse execute(UUID id) {
        Permission permission = permissionRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new PermissionId(id))
                .orElseThrow(() -> new PermissionNotFoundException(new PermissionId(id)));

        return PermissionAssembler.toResponse(permission);
    }

}
