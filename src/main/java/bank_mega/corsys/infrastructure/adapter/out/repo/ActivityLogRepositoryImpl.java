package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.activitylog.ActivityLog;
import bank_mega.corsys.domain.model.activitylog.ActivityLogId;
import bank_mega.corsys.domain.repository.ActivityLogRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ActivityLogJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ActivityLogMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ActivityLogPredicate;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataActivityLogJpaRepository;
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
public class ActivityLogRepositoryImpl implements ActivityLogRepository {

    private final String[] availableSort = {
            "createdAt",
            "httpMethod",
            "statusCode"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataActivityLogJpaRepository springDataAccessLogJpaRepository;

    @Override
    public ActivityLog save(ActivityLog accessLog) {
        return ActivityLogMapper.toDomain(springDataAccessLogJpaRepository.save(
                ActivityLogMapper.toJpaEntity(accessLog)
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
    public Page<@NonNull ActivityLog> findAllPageable(int page, int size, Set<String> expand, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ActivityLogJpaEntity> cQuery = cBuilder.createQuery(ActivityLogJpaEntity.class);
        Root<ActivityLogJpaEntity> root = cQuery.from(ActivityLogJpaEntity.class);

        // Expands fetching
        if (expand.contains("user")) root.fetch("user", JoinType.LEFT);

        // Filter and Sorting
        cQuery.where(ActivityLogPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ActivityLogJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ActivityLogJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ActivityLog> list = resultList.stream()
                .map(jpa -> ActivityLogMapper.toDomain(jpa, expand))
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ActivityLogJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ActivityLogPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ActivityLog> findFirstById(ActivityLogId id, Set<String> expand) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ActivityLogJpaEntity> cQuery = cBuilder.createQuery(ActivityLogJpaEntity.class);
        Root<ActivityLogJpaEntity> root = cQuery.from(ActivityLogJpaEntity.class);

        // Expands fetching
        if (expand.contains("user")) root.fetch("user", JoinType.LEFT);

        // Filter Primary Key
        cQuery.where(ActivityLogPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ActivityLogJpaEntity> query = entityManager.createQuery(cQuery);
        List<ActivityLogJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ActivityLogMapper.toDomain(resultList.getFirst(), expand));
    }

}
