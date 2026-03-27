package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.assembler.UserAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.user.command.CreateUserCommand;
import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.exception.InternalUserNotFoundException;
import bank_mega.corsys.domain.exception.PermissionNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.exception.UserAlreadyExistsException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.domain.model.internaluser.InternalUserName;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.user.*;
import bank_mega.corsys.domain.model.userdetail.UserDetail;
import bank_mega.corsys.domain.model.userpermission.Effect;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import bank_mega.corsys.domain.repository.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final InternalUserRepository internalUserRepository;
    private final UserPermissionRepository userPermissionRepository;
    private final PermissionRepository permissionRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserAssembler userAssembler;

    @Transactional
    public UserResponse execute(CreateUserCommand command, User authPrincipal) {

        InternalUser internalUser = internalUserRepository.findFirstByUserName(new InternalUserName(command.username()))
                .orElseThrow(() -> new InternalUserNotFoundException(new InternalUserName(command.username())));

        UserName userName = new UserName(internalUser.getUserName().value());


        User existingUser = userRepository.findFirstByName(userName).orElse(null);

        if (existingUser != null) {
            if (existingUser.getAudit().deletedAt() == null) {
                throw new UserAlreadyExistsException(existingUser.getName().value());
            } else {
                return restoreUser(existingUser, command, authPrincipal);
            }
        }

        return createNewUser(internalUser, command, authPrincipal);
    }

    private UserResponse restoreUser(User existingUser, CreateUserCommand command, User authPrincipal) {
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(command.roleId()))
                .orElseThrow(() -> new RoleNotFoundException(new RoleCode(command.roleId())));

        existingUser.changePassword(new UserPassword(userRepository.hashPassword(command.password())));
        existingUser.changeRole(role);
        existingUser.updateAudit(authPrincipal.getId().value()); // This will update updatedAt and updatedBy

        User saved = userRepository.save(existingUser);

        handlePermissionOverrides(saved, command);

        return userAssembler.toResponse(saved);
    }

    private UserResponse createNewUser(InternalUser internalUser, CreateUserCommand command, User authPrincipal) {
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(command.roleId()))
                .orElseThrow(() -> new RoleNotFoundException(new RoleCode(command.roleId())));

        User newUser = new User(
                null,
                new UserName(internalUser.getUserName().value()),
                new UserEmail(internalUser.getEmail().value()),
                new UserPassword(userRepository.hashPassword(command.password())),
                role,
                UserType.INTERNAL,
                AuditTrail.create(authPrincipal.getId().value())
        );

        User saved = userRepository.save(newUser);

        UserDetail userDetail = new UserDetail(
                null,
                saved.getId(),
                internalUser.getNama(),
                internalUser.getJabatan(),
                internalUser.getEmail(),
                internalUser.getArea(),
                internalUser.getJobTitle(),
                internalUser.getDirektorat(),
                internalUser.getSex(),
                internalUser.getMobile(),
                internalUser.getTglLahir(),
                internalUser.getUsersCabang(),
                internalUser.getUsersBranch(),
                AuditTrail.create(authPrincipal.getId().value())
        );

        userDetailRepository.save(userDetail);
        saved.setUserDetail(userDetail);

        handlePermissionOverrides(saved, command);

        return userAssembler.toResponse(saved);
    }

    private void handlePermissionOverrides(User user, CreateUserCommand command) {
        if (command.permissionOverrides() != null && !command.permissionOverrides().isEmpty()) {
            // Delete existing permission overrides
            userPermissionRepository.deleteByUserId(user.getId());

            // Insert new permission overrides
            for (CreateUserCommand.PermissionOverride override : command.permissionOverrides()) {
                Permission permission = permissionRepository.findFirstById(PermissionId.of(override.permissionId()))
                        .orElseThrow(() -> new PermissionNotFoundException(PermissionId.of(override.permissionId())));

                UserPermission userPermission = new UserPermission(
                        null,
                        user,
                        permission,
                        Effect.valueOf(override.effect().name())
                );

                userPermissionRepository.save(userPermission);
            }
        } else {
            // If no overrides provided, delete any existing ones
            userPermissionRepository.deleteByUserId(user.getId());
        }
    }
}