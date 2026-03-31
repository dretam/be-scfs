package bank_mega.corsys.application.permission.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.PermissionNotFoundException;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeletePermissionUseCase {

    private final PermissionRepository permissionRepository;

    @Transactional
    public PermissionId execute(UUID id) {
        PermissionId permissionId = new PermissionId(id);

        Permission permission = permissionRepository.findFirstById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));

        permissionRepository.delete(permission);

        return permissionId;
    }

}
