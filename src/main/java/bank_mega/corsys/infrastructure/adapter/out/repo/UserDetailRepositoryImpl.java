package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.userdetail.UserDetail;
import bank_mega.corsys.domain.model.userdetail.UserDetailId;
import bank_mega.corsys.domain.repository.UserDetailRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserDetailJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataUserDetailJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.UserDetailMapper;
import bank_mega.corsys.infrastructure.adapter.out.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserDetailRepositoryImpl implements UserDetailRepository {

    private final SpringDataUserDetailJpaRepository springDataUserDetailJpaRepository;

    private final EntityManager entityManager;

    @Override
    public UserDetail save(UserDetail userDetail) {
        return UserDetailMapper.toDomain(springDataUserDetailJpaRepository.save(
                UserDetailMapper.toJpaEntity(userDetail)
        ));
    }

    @Override
    public void delete(UserDetail userDetail) {
        springDataUserDetailJpaRepository.delete(UserDetailMapper.toJpaEntity(userDetail));
    }

    @Override
    public Optional<UserDetail> findFirstById(UserDetailId id) {
        return springDataUserDetailJpaRepository
                .findFirstById(id.value())
                .map(UserDetailMapper::toDomain);
    }

    @Override
    public Optional<UserDetail> findFirstByIdAndAuditDeletedAtIsNull(UserDetailId id) {
        return springDataUserDetailJpaRepository
                .findFirstByIdAndAuditDeletedAtIsNull(id.value())
                .map(UserDetailMapper::toDomain);
    }

    @Override
    public Optional<UserDetail> findFirstByIdAndAuditDeletedAtIsNull(UserDetailId id, Set<String> expands) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserDetailJpaEntity> cQuery = cBuilder.createQuery(UserDetailJpaEntity.class);
        Root<UserDetailJpaEntity> root = cQuery.from(UserDetailJpaEntity.class);

        // Expands fetching
        if (expands != null && expands.contains("usersCabang")) {
            root.fetch("usersCabang", JoinType.LEFT);
        }

        if (expands != null && expands.contains("usersBranch")) {
            root.fetch("usersBranch", JoinType.LEFT);
        }

        // Filter Primary Key
        cQuery.where(root.get("id").in(id.value()),
                entityManager.getCriteriaBuilder().isNull(root.get("audit").get("deletedAt")));

        TypedQuery<UserDetailJpaEntity> query = entityManager.createQuery(cQuery);
        List<UserDetailJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(UserDetailMapper.toDomain(resultList.getFirst(), expands));
    }

    @Override
    public Optional<UserDetail> findFirstByUserAndAuditDeletedAtIsNull(User user) {
        UserJpaEntity userJpa = UserMapper.toJpaEntity(user);
        return springDataUserDetailJpaRepository
                .findFirstByUserIdAndAuditDeletedAtIsNull(userJpa)
                .map(UserDetailMapper::toDomain);
    }

    @Override
    public Optional<UserDetail> findFirstByUserAndAuditDeletedAtIsNull(User user, Set<String> expands) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserDetailJpaEntity> cQuery = cBuilder.createQuery(UserDetailJpaEntity.class);
        Root<UserDetailJpaEntity> root = cQuery.from(UserDetailJpaEntity.class);

        // Expands fetching
        if (expands != null && expands.contains("usersCabang")) {
            root.fetch("usersCabang", JoinType.LEFT);
        }

        if (expands != null && expands.contains("usersBranch")) {
            root.fetch("usersBranch", JoinType.LEFT);
        }

        // Filter by userId
        Join<UserDetailJpaEntity, UserJpaEntity> userJoin = root.join("userId", JoinType.INNER);
        cQuery.where(userJoin.get("id").in(user.getId().value()),
                entityManager.getCriteriaBuilder().isNull(root.get("audit").get("deletedAt")));

        TypedQuery<UserDetailJpaEntity> query = entityManager.createQuery(cQuery);
        List<UserDetailJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(UserDetailMapper.toDomain(resultList.getFirst(), expands));
    }

}
