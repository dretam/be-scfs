package bank_mega.corsys.application.permission.usecase;

import bank_mega.corsys.application.assembler.PermissionAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.permission.command.CreatePermissionCommand;
import bank_mega.corsys.application.permission.dto.PermissionResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionCode;
import bank_mega.corsys.domain.model.permission.PermissionName;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreatePermissionUseCase {

    private final PermissionRepository permissionRepository;

    @Transactional
    public PermissionResponse execute(CreatePermissionCommand command, User authPrincipal) {
        // Validate permission code uniqueness
        permissionRepository.findFirstByCode(new PermissionCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("Permission code already exists: " + command.code());
                });

        // Create new permission
        Permission newPermission = new Permission(
                null,
                new PermissionName(command.name()),
                new PermissionCode(command.code()),
                command.description(),
                command.menuId() != null ? new MenuId(command.menuId()) : null,
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        Permission saved = permissionRepository.save(newPermission);

        // Convert to response DTO
        return PermissionAssembler.toResponse(saved);
    }

}
