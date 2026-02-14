package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.accesslog.AccessLog;
import bank_mega.corsys.domain.model.accesslog.AccessLogId;
import bank_mega.corsys.domain.repository.AccessLogRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.AccessLogJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.mapper.AccessLogMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.AccessLogPredicate;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataAccessLogJpaRepository;
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

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
@RequiredArgsConstructor
public class AccessLogRepositoryImpl implements AccessLogRepository {

    private final String[] availableSort = {
            "createdAt",
            "httpMethod",
            "statusCode"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataAccessLogJpaRepository springDataAccessLogJpaRepository;

    @Override
    public AccessLog save(AccessLog accessLog) {
        return AccessLogMapper.toDomain(springDataAccessLogJpaRepository.save(
                AccessLogMapper.toJpaEntity(accessLog)
        ));
    }

    @Override
    public long count() {
        return springDataAccessLogJpaRepository.count();
    }

    @Override
    public void housekeeping(Instant time) {
        springDataAccessLogJpaRepository.deleteByCreatedAtLessThanEqual(time);
    }

    @Override
    public Page<@NonNull AccessLog> findAllPageable(int page, int size, Set<String> expand, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<AccessLogJpaEntity> cQuery = cBuilder.createQuery(AccessLogJpaEntity.class);
        Root<AccessLogJpaEntity> root = cQuery.from(AccessLogJpaEntity.class);

        // Expands fetching
        if (expand.contains("user")) root.fetch("user", JoinType.LEFT);

        // Filter and Sorting
        cQuery.where(AccessLogPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<AccessLogJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<AccessLogJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<AccessLog> list = resultList.stream()
                .map(jpa -> AccessLogMapper.toDomain(jpa, expand))
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        AccessLogJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                AccessLogPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<AccessLog> findFirstById(AccessLogId id, Set<String> expand) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<AccessLogJpaEntity> cQuery = cBuilder.createQuery(AccessLogJpaEntity.class);
        Root<AccessLogJpaEntity> root = cQuery.from(AccessLogJpaEntity.class);

        // Expands fetching
        if (expand.contains("user")) root.fetch("user", JoinType.LEFT);

        // Filter Primary Key
        cQuery.where(AccessLogPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<AccessLogJpaEntity> query = entityManager.createQuery(cQuery);
        List<AccessLogJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(AccessLogMapper.toDomain(resultList.getFirst(), expand));
    }

}
