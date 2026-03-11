package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.userdetail.UserDetail;
import bank_mega.corsys.domain.model.userdetail.UserDetailId;
import lombok.NonNull;

import java.util.Optional;
import java.util.Set;

public interface UserDetailRepository {

    UserDetail save(UserDetail userDetail);

    void delete(UserDetail userDetail);

    Optional<UserDetail> findFirstById(UserDetailId id);

    Optional<UserDetail> findFirstByIdAndAuditDeletedAtIsNull(UserDetailId id);

    Optional<UserDetail> findFirstByIdAndAuditDeletedAtIsNull(UserDetailId id, Set<String> expands);

    Optional<UserDetail> findFirstByUserAndAuditDeletedAtIsNull(User user);

    Optional<UserDetail> findFirstByUserAndAuditDeletedAtIsNull(User user, Set<String> expands);

}
