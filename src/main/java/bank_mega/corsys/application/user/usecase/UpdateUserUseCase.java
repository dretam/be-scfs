package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.assembler.UserAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.user.command.UpdateUserCommand;
import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.exception.PermissionNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.exception.UserExistingPasswordException;
import bank_mega.corsys.domain.exception.UserNotFoundException;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.model.user.*;
import bank_mega.corsys.domain.model.userpermission.Effect;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import bank_mega.corsys.domain.repository.PermissionRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.domain.repository.UserPermissionRepository;
import bank_mega.corsys.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
@UseCase
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserPermissionRepository userPermissionRepository;
    private final UserAssembler userAssembler;

    @Transactional
    public UserResponse execute(UpdateUserCommand command, User authPrincipal) {

        User user = findUser(command.id());

        updateUserData(user, command);

        user.updateAudit(authPrincipal.getId().value());

        User saved = userRepository.save(user);

        handlePermissionOverrides(saved, command);

        return userAssembler.toResponse(saved);
    }

    private User findUser(Long userId) {
        return userRepository
                .findFirstByIdAndAuditDeletedAtIsNull(new UserId(userId))
                .orElseThrow(() -> new UserNotFoundException(new UserId(userId)));
    }

    private void updateUserData(User user, UpdateUserCommand command) {

        command.roleId().ifPresent(roleId -> {
            Role role = roleRepository
                    .findFirstByIdAndAuditDeletedAtIsNull(new RoleId(roleId))
                    .orElseThrow(() -> new RoleNotFoundException(new RoleId(roleId)));

            user.changeRole(role);
        });

        command.name().ifPresent(name ->
                user.changeName(new UserName(name))
        );

        command.email().ifPresent(email ->
                user.changeEmail(new UserEmail(email))
        );

        handlePasswordChange(user, command);
    }

    private void handlePasswordChange(User user, UpdateUserCommand command) {

        command.existingPassword().ifPresent(existingPassword -> {

            boolean match = BCrypt.checkpw(existingPassword, user.getPassword().value());

            if (!match) {
                throw new UserExistingPasswordException();
            }

            command.password().ifPresent(newPassword ->
                    user.changePassword(
                            new UserPassword(userRepository.hashPassword(newPassword))
                    )
            );
        });
    }

    private void handlePermissionOverrides(User user, UpdateUserCommand command) {

        if (command.permissionOverrides() != null && !command.permissionOverrides().isEmpty()) {

            userPermissionRepository.deleteByUserId(user.getId());

            for (UpdateUserCommand.PermissionOverride override : command.permissionOverrides()) {

                Permission permission = permissionRepository
                        .findFirstById(PermissionId.of(override.permissionId()))
                        .orElseThrow(() ->
                                new PermissionNotFoundException(PermissionId.of(override.permissionId()))
                        );

                UserPermission userPermission = new UserPermission(
                        null,
                        user,
                        permission,
                        Effect.valueOf(override.effect().name())
                );

                userPermissionRepository.save(userPermission);
            }

        } else {
            userPermissionRepository.deleteByUserId(user.getId());
        }
    }
}