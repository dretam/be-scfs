package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserEmail;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.user.UserName;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface UserRepository {

    User save(User user);

    void delete(User user);

    long count();

    Page<@NonNull User> findAllPageable(int page, int size, Set<String> expand, String sort, String filter);

    Optional<User> findFirstById(UserId id);

    Optional<User> findFirstByIdAndAuditDeletedAtIsNull(UserId id);

    Optional<User> findFirstByIdAndAuditDeletedAtIsNull(UserId id, Set<String> expands);

    Optional<User> findFirstByEmailAndAuditDeletedAtIsNull(UserEmail email);

    Optional<User> findFirstByNameAndAuditDeletedAtIsNull(UserName name);

    Optional<User> findFirstByName(UserName name);

    Optional<User> findFirstByNameAndIdNot(UserName name, UserId id);

    Optional<User> findFirstByEmail(UserEmail email);

    Optional<User> findFirstByEmailAndIdNot(UserEmail email, UserId id);

    String hashPassword(String password);

}
