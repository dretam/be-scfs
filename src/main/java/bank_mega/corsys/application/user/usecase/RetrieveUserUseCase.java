package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.UserNotFoundException;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;


@UseCase
@RequiredArgsConstructor
public class RetrieveUserUseCase {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User execute(UUID id, Set<String> expands) {
        return userRepository.findFirstByIdAndAuditDeletedAtIsNull(new UserId(id), expands).orElseThrow(
                () -> new UserNotFoundException(new UserId(id))
        );
    }

}
