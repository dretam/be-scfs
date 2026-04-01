package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.UserNotFoundException;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@UseCase
@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    @Transactional
    public UserId execute(UUID id) {
        // 1. Validasi bahwa user ada
        User user = userRepository.findFirstById(new UserId(id)).orElseThrow(
                () -> new UserNotFoundException(new UserId(id))
        );

        // 2. Delete repository (masuk ke infra → mapper → jpa)
        userRepository.delete(user);
        return new UserId(id);
    }

}
