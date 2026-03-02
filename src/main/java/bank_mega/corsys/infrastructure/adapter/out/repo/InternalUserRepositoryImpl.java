package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.domain.model.internaluser.InternalUserEmail;
import bank_mega.corsys.domain.model.internaluser.InternalUserName;
import bank_mega.corsys.domain.repository.InternalUserRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.InternalUserJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.mapper.InternalUserMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.InternalUserPredicate;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataInternalUserJpaRepository;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import bank_mega.corsys.infrastructure.util.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
@RequiredArgsConstructor
public class InternalUserRepositoryImpl implements InternalUserRepository {

    private final String[] availableSort = {
            "userName",
            "nama",
            "email",
            "audit.createdAt"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataInternalUserJpaRepository springDataInternalUserJpaRepository;

    @Override
    public long count() {
        return springDataInternalUserJpaRepository.count();
    }

    @Override
    public Page<@NonNull InternalUser> findAllPageable(int page, int size, Set<String> expands, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<InternalUserJpaEntity> cQuery = cBuilder.createQuery(InternalUserJpaEntity.class);
        Root<InternalUserJpaEntity> root = cQuery.from(InternalUserJpaEntity.class);

        // Expands fetching
        if (expands.contains("usersCabang")) root.fetch("usersCabang", JoinType.LEFT);
        if (expands.contains("usersBranch")) root.fetch("usersBranch", JoinType.LEFT);

        // Filter and Sorting
        cQuery.where(InternalUserPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<InternalUserJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<InternalUserJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<InternalUser> list = resultList.stream()
                .map(jpa -> InternalUserMapper.toDomain(jpa, expands))
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        InternalUserJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                InternalUserPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<InternalUser> findFirstByUserName(InternalUserName userName) {
        return springDataInternalUserJpaRepository
                .findFirstByUserName(userName.value())
                .map(InternalUserMapper::toDomain);
    }


    @Override
    public Optional<InternalUser> findFirstByUserName(InternalUserName userName, Set<String> expand) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<InternalUserJpaEntity> cQuery = cBuilder.createQuery(InternalUserJpaEntity.class);
        Root<InternalUserJpaEntity> root = cQuery.from(InternalUserJpaEntity.class);

        // Expands fetching
        if (expand.contains("usersCabang")) root.fetch("usersCabang", JoinType.LEFT);
        if (expand.contains("usersBranch")) root.fetch("usersBranch", JoinType.LEFT);

        // Filter Primary Key
        cQuery.where(InternalUserPredicate.retrieveBuild(cBuilder, root, userName));

        TypedQuery<InternalUserJpaEntity> query = entityManager.createQuery(cQuery);
        List<InternalUserJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(InternalUserMapper.toDomain(resultList.getFirst(), expand));
    }

    @Override
    public Optional<InternalUser> findFirstByEmail(InternalUserEmail email) {
        return springDataInternalUserJpaRepository
                .findFirstByEmail(email.value())
                .map(InternalUserMapper::toDomain);
    }

}
