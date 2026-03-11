package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.assembler.UserAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.user.command.CreateUserCommand;
import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.exception.InternalUserNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.exception.UserAlreadyExistsException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.domain.model.internaluser.InternalUserName;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.model.user.*;
import bank_mega.corsys.domain.model.userdetail.UserDetail;
import bank_mega.corsys.domain.repository.InternalUserRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.domain.repository.UserDetailRepository;
import bank_mega.corsys.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final InternalUserRepository internalUserRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserAssembler userAssembler;

    @Transactional
    public UserResponse execute(CreateUserCommand command, User authPrincipal) {

        // Check if InternalUser exists
        InternalUser internalUser = internalUserRepository.findFirstByUserName(new InternalUserName(command.username()))
                .orElseThrow(() -> new InternalUserNotFoundException(new InternalUserName(command.username())));

        UserName userName = new UserName(internalUser.getUserName().value());


        // Check for ANY existing user with this username (including soft-deleted)
        User existingUser = userRepository.findFirstByName(userName).orElse(null);

        if (existingUser != null) {
            if (existingUser.getAudit().deletedAt() == null) {
                // Active user exists
                throw new UserAlreadyExistsException(existingUser.getName().value());
            } else {
                // Soft-deleted user exists - restore it
                return restoreUser(existingUser, command, authPrincipal);
            }
        }

        // No existing user found - create new one
        return createNewUser(internalUser, command, authPrincipal);
    }

    private UserResponse restoreUser(User existingUser, CreateUserCommand command, User authPrincipal) {
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleId(command.roleId()))
                .orElseThrow(() -> new RoleNotFoundException(new RoleId(command.roleId())));

        existingUser.changePassword(new UserPassword(userRepository.hashPassword(command.password())));
        existingUser.changeRole(role);

        existingUser.updateAudit(authPrincipal.getId().value()); // This will update updatedAt and updatedBy

        User saved = userRepository.save(existingUser);
        return userAssembler.toResponse(saved);
    }

    private UserResponse createNewUser(InternalUser internalUser, CreateUserCommand command, User authPrincipal) {
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleId(command.roleId()))
                .orElseThrow(() -> new RoleNotFoundException(new RoleId(command.roleId())));

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

        // Create UserDetail from InternalUser data (only selected fields)
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

        // Set the userDetail relation
        saved.setUserDetail(userDetail);

        return userAssembler.toResponse(saved);
    }
}