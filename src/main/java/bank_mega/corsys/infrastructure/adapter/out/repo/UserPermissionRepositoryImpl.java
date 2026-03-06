package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import bank_mega.corsys.domain.model.userpermission.UserPermissionId;
import bank_mega.corsys.domain.repository.UserPermissionRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.UserPermissionIdEmbeddable;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataUserPermissionJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.UserPermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPermissionRepositoryImpl implements UserPermissionRepository {

    private final SpringDataUserPermissionJpaRepository springDataUserPermissionJpaRepository;

    @Override
    public UserPermission save(UserPermission userPermission) {
        // Save and return the same domain object (avoid lazy loading issue on return)
        springDataUserPermissionJpaRepository.save(
                UserPermissionMapper.toJpaEntity(userPermission)
        );
        return userPermission;
    }

    @Override
    public void delete(UserPermission userPermission) {
        springDataUserPermissionJpaRepository.delete(
                UserPermissionMapper.toJpaEntity(userPermission)
        );
    }

    @Override
    public Optional<UserPermission> findFirstById(UserPermissionId id) {
        UserPermissionIdEmbeddable embeddable = new UserPermissionIdEmbeddable(
                id.userId(),
                id.permissionId()
        );
        return springDataUserPermissionJpaRepository.findById(embeddable)
                .map(UserPermissionMapper::toDomain);
    }

    @Override
    public List<UserPermission> findAllByUserId(UserId userId) {
        return springDataUserPermissionJpaRepository.findAllByUserId(userId.value())
                .stream()
                .map(UserPermissionMapper::toDomain)
                .toList();
    }

    @Override
    public List<UserPermission> findAllAllowByUserId(UserId userId) {
        return springDataUserPermissionJpaRepository.findAllAllowByUserId(userId.value())
                .stream()
                .map(UserPermissionMapper::toDomain)
                .toList();
    }

    @Override
    public List<UserPermission> findAllDenyByUserId(UserId userId) {
        return springDataUserPermissionJpaRepository.findAllDenyByUserId(userId.value())
                .stream()
                .map(UserPermissionMapper::toDomain)
                .toList();
    }

}
