package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.assembler.UserAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.user.command.UpdateUserCommand;
import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.exception.UserExistingPasswordException;
import bank_mega.corsys.domain.exception.UserNotFoundException;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.model.user.*;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public UserResponse execute(UpdateUserCommand command, User authPrincipal) {

        // 1. Validasi bahwa user ada
        User user = userRepository.findFirstByIdAndAuditDeletedAtIsNull(new UserId(command.id())).orElseThrow(
                () -> new UserNotFoundException(new UserId(command.id()))
        );

        // 2. Transaksi
        command.roleId().ifPresent(roleId -> {
            // 2.1. Validasi bahwa role ada
            Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleId(roleId)).orElseThrow(
                    () -> new RoleNotFoundException(new RoleId(roleId))
            );
            user.changeRole(role);
        });

        command.name().ifPresent(name -> user.changeName(new UserName(name)));

        command.email().ifPresent(email -> user.changeEmail(new UserEmail(email)));

        command.existingPassword().ifPresent(existingPassword -> {

            // 1. Validasi password sekarang
            boolean checkPassword = BCrypt.checkpw(existingPassword, user.getPassword().value());
            if (!checkPassword) {
                throw new UserExistingPasswordException();
            }

            command.password().ifPresent(password -> user.changePassword(
                    new UserPassword(userRepository.hashPassword(password))
            ));

        });

        user.updateAudit(authPrincipal.getId().value());

        // 3. simpan di repository (masuk ke infra → mapper → jpa)
        User saved = userRepository.save(user);

        // 4. convert ke response DTO
        return UserAssembler.toResponse(saved);
    }

}
