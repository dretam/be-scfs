package bank_mega.corsys.application.internaluser.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.domain.repository.InternalUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@UseCase
@RequiredArgsConstructor
public class PageInternalUserUseCase {

    private final InternalUserRepository internalUserRepository;

    @Transactional(readOnly = true)
    public Page<@NonNull InternalUser> execute(int page, int size, Set<String> expands, String sort, String filter) {
        return internalUserRepository.findAllPageable(page, size, expands, sort, filter);
    }

}
