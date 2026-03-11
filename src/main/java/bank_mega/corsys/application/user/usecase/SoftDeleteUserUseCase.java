package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.assembler.UserAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.user.command.SoftDeleteUserCommand;
import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.exception.UserNotFoundException;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class SoftDeleteUserUseCase {

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    @Transactional
    public UserResponse execute(SoftDeleteUserCommand command, User authPrincipal) {

        // 1. Validasi bahwa user ada
        User user = userRepository.findFirstByIdAndAuditDeletedAtIsNull(new UserId(command.id())).orElseThrow(
                () -> new UserNotFoundException(new UserId(command.id()))
        );

        // 2. Update Soft Delete
        user.deleteAudit(authPrincipal.getId().value());

        // 3. Simpan di repository (masuk ke infra → mapper → jpa)
        User saved = userRepository.save(user);

        // 4. convert ke response DTO
        return userAssembler.toResponse(saved);
    }

}
