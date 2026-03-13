package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import bank_mega.corsys.domain.model.userpermission.UserPermissionId;

import java.util.List;
import java.util.Optional;

public interface UserPermissionRepository {

    UserPermission save(UserPermission userPermission);

    void delete(UserPermission userPermission);

    void deleteByUserId(UserId userId);

    Optional<UserPermission> findFirstById(UserPermissionId id);

    List<UserPermission> findAllByUserId(UserId userId);

    List<UserPermission> findAllAllowByUserId(UserId userId);

    List<UserPermission> findAllDenyByUserId(UserId userId);

}
