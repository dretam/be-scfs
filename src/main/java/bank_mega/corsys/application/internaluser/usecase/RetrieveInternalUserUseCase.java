package bank_mega.corsys.application.internaluser.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.InternalUserNotFoundException;
import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.domain.model.internaluser.InternalUserName;
import bank_mega.corsys.domain.repository.InternalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@UseCase
@RequiredArgsConstructor
public class RetrieveInternalUserUseCase {

    private final InternalUserRepository internalUserRepository;

    @Transactional(readOnly = true)
    public InternalUser execute(String userName, Set<String> expands) {
        return internalUserRepository.findFirstByUserName(new InternalUserName(userName), expands).orElseThrow(
                () -> new InternalUserNotFoundException(new InternalUserName(userName))
        );
    }

}
