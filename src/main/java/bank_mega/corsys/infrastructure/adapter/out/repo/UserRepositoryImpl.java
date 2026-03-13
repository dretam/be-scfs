package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserEmail;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.user.UserName;
import bank_mega.corsys.domain.repository.UserRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.mapper.UserMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.UserPredicate;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataUserJpaRepository;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import bank_mega.corsys.infrastructure.util.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "name",
            "email"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataUserJpaRepository springDataUserJpaRepository;

    @Override
    public User save(User user) {
        return UserMapper.toDomain(springDataUserJpaRepository.save(
                UserMapper.toJpaEntity(user)
        ));
    }

    @Override
    public void delete(User user) {
        springDataUserJpaRepository.delete(UserMapper.toJpaEntity(user));
    }

    @Override
    public long count() {
        return springDataUserJpaRepository.count();
    }

    @Override
    public Page<@NonNull User> findAllPageable(int page, int size, Set<String> expands, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserJpaEntity> cQuery = cBuilder.createQuery(UserJpaEntity.class);
        Root<UserJpaEntity> root = cQuery.from(UserJpaEntity.class);

        // Expands fetching
        if (expands.contains("role")) root.fetch("role", JoinType.LEFT);

        if (expands.contains("cabang")) root.fetch("cabang", JoinType.LEFT);

        if (expands.contains("userDetail")) root.fetch("userDetail", JoinType.LEFT);

        // Filter and Sorting
        cQuery.where(UserPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<UserJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<UserJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<User> list = resultList.stream()
                .map(jpa -> UserMapper.toDomain(jpa, expands))
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        UserJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                UserPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<User> findFirstById(UserId id) {
        return springDataUserJpaRepository
                .findFirstById(id.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findFirstByIdAndAuditDeletedAtIsNull(UserId id) {
        return springDataUserJpaRepository
                .findFirstByIdAndAuditDeletedAtIsNull(id.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findFirstByIdAndAuditDeletedAtIsNull(UserId id, Set<String> expands) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserJpaEntity> cq = cb.createQuery(UserJpaEntity.class);

        Root<UserJpaEntity> root = cq.from(UserJpaEntity.class);

        boolean role = expands != null && expands.contains("role");
        boolean permissions = expands != null && expands.contains("permissions");
        boolean menus = expands != null && expands.contains("menus");
        boolean userDetail = expands != null && expands.contains("userDetail");

        if (role) {
            Fetch<UserJpaEntity, ?> roleFetch = root.fetch("role", JoinType.LEFT);
            if (permissions) {
                Fetch<?, ?> permissionFetch = roleFetch.fetch("permissions", JoinType.LEFT);
                if (menus) {
                    permissionFetch.fetch("menu", JoinType.LEFT);
                }
            }
        }

        if (userDetail) {
            root.fetch("userDetail", JoinType.LEFT);
        }

        cq.select(root)
                .where(UserPredicate.retrieveBuild(cb, root, id))
                .distinct(true);

        TypedQuery<UserJpaEntity> query = entityManager.createQuery(cq);

        return query.getResultStream()
                .findFirst()
                .map(entity -> UserMapper.toDomain(entity, expands));
    }

    @Override
    public Optional<User> findFirstByEmailAndAuditDeletedAtIsNull(UserEmail email) {
        return springDataUserJpaRepository
                .findFirstByEmailAndAuditDeletedAtIsNull(email.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findFirstByNameAndAuditDeletedAtIsNull(UserName name) {
        return springDataUserJpaRepository
                .findFirstByNameAndAuditDeletedAtIsNull(name.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findFirstByName(UserName name) {
        return springDataUserJpaRepository
                .findFirstByName(name.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findFirstByNameAndIdNot(UserName name, UserId id) {
        return springDataUserJpaRepository
                .findFirstByNameAndIdNot(name.value(), id.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findFirstByEmail(UserEmail email) {
        return springDataUserJpaRepository
                .findFirstByEmail(email.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findFirstByEmailAndIdNot(UserEmail email, UserId id) {
        return springDataUserJpaRepository
                .findFirstByEmailAndIdNot(email.value(), id.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
