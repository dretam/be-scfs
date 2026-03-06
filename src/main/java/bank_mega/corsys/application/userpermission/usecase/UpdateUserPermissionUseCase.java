package bank_mega.corsys.application.userpermission.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.userpermission.command.UpdateUserPermissionCommand;
import bank_mega.corsys.application.userpermission.dto.UserPermissionResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.userpermission.Effect;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import bank_mega.corsys.domain.model.userpermission.UserPermissionId;
import bank_mega.corsys.domain.repository.PermissionRepository;
import bank_mega.corsys.domain.repository.UserPermissionRepository;
import bank_mega.corsys.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case to update a user permission override.
 * Allows changing the effect (ALLOW/DENY) of an existing override.
 */
@UseCase
@RequiredArgsConstructor
public class UpdateUserPermissionUseCase {

    private final UserPermissionRepository userPermissionRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    public UserPermissionResponse execute(UpdateUserPermissionCommand command, User authPrincipal) {
        // Validate user exists
        User user = userRepository.findFirstByIdAndAuditDeletedAtIsNull(new UserId(command.userId()))
                .orElseThrow(() -> new DomainRuleViolationException("User not found: " + command.userId()));

        // Validate permission exists
        Permission permission = permissionRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new PermissionId(command.permissionId()))
                .orElseThrow(() -> new DomainRuleViolationException("Permission not found: " + command.permissionId()));

        // Validate effect
        Effect effect;
        try {
            effect = Effect.valueOf(command.effect().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DomainRuleViolationException("Invalid effect: " + command.effect() + ". Must be ALLOW or DENY.");
        }

        // Find existing user permission override
        UserPermissionId id = new UserPermissionId(command.userId(), command.permissionId());
        UserPermission userPermission = userPermissionRepository.findFirstById(id)
                .orElseThrow(() -> new DomainRuleViolationException(
                        "User permission override not found for user " + command.userId() +
                        " and permission " + command.permissionId()));

        // Update the effect
        userPermission.changeEffect(effect);

        // Save to repository
        UserPermission updated = userPermissionRepository.save(userPermission);

        // Convert to response DTO
        return UserPermissionResponse.builder()
                .userId(updated.getId().userId())
                .permissionId(updated.getId().permissionId())
                .permissionCode(updated.getPermission().getCode().value())
                .permissionName(updated.getPermission().getName().value())
                .effect(updated.getEffect().name())
                .build();
    }

}
