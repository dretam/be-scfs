package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@UseCase
@RequiredArgsConstructor
public class PageUserUseCase {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<@NonNull User> execute(int page, int size, Set<String> expands, String sort, String filter) {
        return userRepository.findAllPageable(page, size, expands, sort, filter);
    }

}
