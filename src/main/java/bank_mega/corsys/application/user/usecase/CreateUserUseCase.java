package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.assembler.UserAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.user.command.CreateUserCommand;
import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.model.user.*;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;


@UseCase
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public UserResponse execute(CreateUserCommand command, User authPrincipal) {

        // 1. Validasi bahwa role ada
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleId(command.roleId())).orElseThrow(
                () -> new RoleNotFoundException(new RoleId(command.roleId()))
        );

        // 2. Save user
        User newUser = new User(
                null,
                new UserName(command.name()),
                new UserEmail(command.email()),
                new UserPassword(userRepository.hashPassword(command.password())),
                role,
                AuditTrail.create(authPrincipal.getId().value())
        );

        // 3. simpan di repository (masuk ke infra → mapper → jpa)
        User saved = userRepository.save(newUser);

        // 4. convert ke response DTO
        return UserAssembler.toResponse(saved);
    }

}
