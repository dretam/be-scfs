package bank_mega.corsys.application.permission.usecase;

import bank_mega.corsys.application.assembler.PermissionAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.permission.command.UpdatePermissionCommand;
import bank_mega.corsys.application.permission.dto.PermissionResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.PermissionNotFoundException;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionCode;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.permission.PermissionName;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UpdatePermissionUseCase {

    private final PermissionRepository permissionRepository;

    @Transactional
    public PermissionResponse execute(UpdatePermissionCommand command, User authPrincipal) {
        // Find existing permission
        Permission permission = permissionRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new PermissionId(command.id()))
                .orElseThrow(() -> new PermissionNotFoundException(new PermissionId(command.id())));

        // Validate code uniqueness if code is being updated
        command.code().ifPresent(newCode -> {
            permissionRepository.findFirstByCode(new PermissionCode(newCode))
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(permission.getId())) {
                            throw new DomainRuleViolationException("Permission code already exists: " + newCode);
                        }
                    });
        });

        // Update fields
        command.name().ifPresent(name -> permission.changeName(new PermissionName(name)));
        command.code().ifPresent(code -> permission.changeCode(new PermissionCode(code)));
        command.description().ifPresent(permission::changeDescription);

        // Update audit
        permission.updateAudit(authPrincipal.getId().value());

        // Save to repository
        Permission saved = permissionRepository.save(permission);

        // Convert to response DTO
        return PermissionAssembler.toResponse(saved);
    }

}
