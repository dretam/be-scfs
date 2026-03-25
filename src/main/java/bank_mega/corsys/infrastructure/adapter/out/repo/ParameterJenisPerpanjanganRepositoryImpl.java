package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjangan;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganCode;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganId;
import bank_mega.corsys.domain.repository.ParameterJenisPerpanjanganRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisPerpanjanganJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataParameterJenisPerpanjanganJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ParameterJenisPerpanjanganMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ParameterJenisPerpanjanganPredicate;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import bank_mega.corsys.infrastructure.util.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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

@Repository
@RequiredArgsConstructor
public class ParameterJenisPerpanjanganRepositoryImpl implements ParameterJenisPerpanjanganRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "value"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataParameterJenisPerpanjanganJpaRepository springDataParameterJenisPerpanjanganJpaRepository;

    @Override
    public ParameterJenisPerpanjangan save(ParameterJenisPerpanjangan parameterJenisPerpanjangan) {
        return ParameterJenisPerpanjanganMapper.toDomain(springDataParameterJenisPerpanjanganJpaRepository.save(
                ParameterJenisPerpanjanganMapper.toJpaEntity(parameterJenisPerpanjangan)
        ));
    }

    @Override
    public void delete(ParameterJenisPerpanjangan parameterJenisPerpanjangan) {
        springDataParameterJenisPerpanjanganJpaRepository.delete(ParameterJenisPerpanjanganMapper.toJpaEntity(parameterJenisPerpanjangan));
    }

    @Override
    public long count() {
        return springDataParameterJenisPerpanjanganJpaRepository.count();
    }

    @Override
    public Page<@NonNull ParameterJenisPerpanjangan> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterJenisPerpanjanganJpaEntity> cQuery = cBuilder.createQuery(ParameterJenisPerpanjanganJpaEntity.class);
        Root<ParameterJenisPerpanjanganJpaEntity> root = cQuery.from(ParameterJenisPerpanjanganJpaEntity.class);

        // Filter and Sorting
        cQuery.where(ParameterJenisPerpanjanganPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ParameterJenisPerpanjanganJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ParameterJenisPerpanjanganJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ParameterJenisPerpanjangan> list = resultList.stream()
                .map(ParameterJenisPerpanjanganMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ParameterJenisPerpanjanganJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ParameterJenisPerpanjanganPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ParameterJenisPerpanjangan> findFirstById(ParameterJenisPerpanjanganId id) {
        return springDataParameterJenisPerpanjanganJpaRepository
                .findFirstByCode(id.value())
                .map(ParameterJenisPerpanjanganMapper::toDomain);
    }

    @Override
    public Optional<ParameterJenisPerpanjangan> findFirstByIdAndAuditDeletedAtIsNull(ParameterJenisPerpanjanganId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterJenisPerpanjanganJpaEntity> cQuery = cBuilder.createQuery(ParameterJenisPerpanjanganJpaEntity.class);
        Root<ParameterJenisPerpanjanganJpaEntity> root = cQuery.from(ParameterJenisPerpanjanganJpaEntity.class);

        // Filter Primary Key
        cQuery.where(ParameterJenisPerpanjanganPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ParameterJenisPerpanjanganJpaEntity> query = entityManager.createQuery(cQuery);
        List<ParameterJenisPerpanjanganJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ParameterJenisPerpanjanganMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<ParameterJenisPerpanjangan> findFirstByCode(ParameterJenisPerpanjanganCode code) {
        return springDataParameterJenisPerpanjanganJpaRepository
                .findFirstByCode(code.value())
                .map(ParameterJenisPerpanjanganMapper::toDomain);
    }

}
