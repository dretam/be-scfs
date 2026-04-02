package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.assembler.UserAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.user.command.UpdateUserCommand;
import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.exception.CompanyNotFoundException;
import bank_mega.corsys.domain.exception.PermissionNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.exception.UserNotFoundException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.model.company.CompanyId;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.user.*;
import bank_mega.corsys.domain.model.userpermission.Effect;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import bank_mega.corsys.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final PermissionRepository permissionRepository;
    private final UserPermissionRepository userPermissionRepository;
    private final UserAssembler userAssembler;

    @Transactional
    public UserResponse execute(UpdateUserCommand command, User authPrincipal) {

        User user = findUser(command.id());

        command.roleId().ifPresent(roleId -> {
            Role role = roleRepository
                    .findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(roleId))
                    .orElseThrow(() -> new RoleNotFoundException(new RoleCode(roleId)));

            user.changeRole(role);
        });

        if(command.companyId() != null) {
            Company company = companyRepository
                    .findFirstByIdAndAuditDeletedAtIsNull(new CompanyId(command.companyId()))
                    .orElseThrow(() -> new CompanyNotFoundException(new CompanyId(command.companyId())));

            user.changeCompany(company);
        }

        if(command.password() != null) {
            user.changePassword(
                    new UserPassword(userRepository.hashPassword(command.password()))
            );
        }

        if(command.fullName() != null) {
            user.changeFullName(
                    new UserFullName(command.fullName())
            );
        }

        if(command.email() != null) {
            user.changeEmail(
                    new UserEmail(command.email())
            );
        }

        if(command.isActive() != null) {
            user.changeIsActive(
                    new UserIsActive(command.isActive())
            );
        }

        if(command.photoPath() != null) {
            user.changePhotoPath(
                    new UserPhotoPath(command.photoPath())
            );
        }

        user.updateAudit(authPrincipal.getId().value());

        User saved = userRepository.save(user);

        handlePermissionOverrides(saved, command);

        return userAssembler.toResponse(saved);
    }

    private User findUser(UUID userId) {
        return userRepository
                .findFirstByIdAndAuditDeletedAtIsNull(new UserId(userId))
                .orElseThrow(() -> new UserNotFoundException(new UserId(userId)));
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