package bank_mega.corsys.application.userpermission.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.userpermission.command.CreateUserPermissionCommand;
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

@UseCase
@RequiredArgsConstructor
public class CreateUserPermissionUseCase {

    private final UserPermissionRepository userPermissionRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    public UserPermissionResponse execute(CreateUserPermissionCommand command, User authPrincipal) {
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

        // Create user permission override
        UserPermissionId id = new UserPermissionId(command.userId(), command.permissionId());
        UserPermission userPermission = new UserPermission(
                id,
                user,
                permission,
                effect
        );

        // Save to repository
        UserPermission saved = userPermissionRepository.save(userPermission);

        // Convert to response DTO
        return UserPermissionResponse.builder()
                .userId(saved.getId().userId())
                .permissionId(saved.getId().permissionId())
                .permissionCode(saved.getPermission().getCode().value())
                .permissionName(saved.getPermission().getName().value())
                .effect(saved.getEffect().name())
                .build();
    }

}
