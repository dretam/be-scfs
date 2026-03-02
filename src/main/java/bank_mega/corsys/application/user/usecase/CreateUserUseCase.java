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
import bank_mega.corsys.domain.repository.InternalUserRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final InternalUserRepository internalUserRepository;

    @Transactional
    public UserResponse execute(CreateUserCommand command, User authPrincipal) {

        // Check if InternalUser exists
        InternalUser internalUser = internalUserRepository.findFirstByUserName(new InternalUserName(command.username()))
                .orElseThrow(() -> new InternalUserNotFoundException(new InternalUserName(command.username())));

        if (userRepository.findFirstByNameAndAuditDeletedAtIsNull(new UserName(internalUser.getUserName().value())).isPresent()) {
            throw new UserAlreadyExistsException("User already exists for this internal user");
        }

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

        return UserAssembler.toResponse(saved);
    }
}