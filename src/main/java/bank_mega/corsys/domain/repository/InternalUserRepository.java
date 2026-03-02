package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.domain.model.internaluser.InternalUserEmail;
import bank_mega.corsys.domain.model.internaluser.InternalUserName;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface InternalUserRepository {

    long count();

    Page<@NonNull InternalUser> findAllPageable(int page, int size, Set<String> expand, String sort, String filter);

    Optional<InternalUser> findFirstByUserName(InternalUserName userName);

    Optional<InternalUser> findFirstByUserName(InternalUserName userName, Set<String> expands);

    Optional<InternalUser> findFirstByEmail(InternalUserEmail email);
}
