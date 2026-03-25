package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerima;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaCode;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaId;
import bank_mega.corsys.domain.repository.ParameterStatusKependudukanPenerimaRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterStatusKependudukanPenerimaJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataParameterStatusKependudukanPenerimaJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ParameterStatusKependudukanPenerimaMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ParameterStatusKependudukanPenerimaPredicate;
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
public class ParameterStatusKependudukanPenerimaRepositoryImpl implements ParameterStatusKependudukanPenerimaRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "value"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataParameterStatusKependudukanPenerimaJpaRepository springDataParameterStatusKependudukanPenerimaJpaRepository;

    @Override
    public ParameterStatusKependudukanPenerima save(ParameterStatusKependudukanPenerima parameterStatusKependudukanPenerima) {
        return ParameterStatusKependudukanPenerimaMapper.toDomain(springDataParameterStatusKependudukanPenerimaJpaRepository.save(
                ParameterStatusKependudukanPenerimaMapper.toJpaEntity(parameterStatusKependudukanPenerima)
        ));
    }

    @Override
    public void delete(ParameterStatusKependudukanPenerima parameterStatusKependudukanPenerima) {
        springDataParameterStatusKependudukanPenerimaJpaRepository.delete(ParameterStatusKependudukanPenerimaMapper.toJpaEntity(parameterStatusKependudukanPenerima));
    }

    @Override
    public long count() {
        return springDataParameterStatusKependudukanPenerimaJpaRepository.count();
    }

    @Override
    public Page<@NonNull ParameterStatusKependudukanPenerima> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterStatusKependudukanPenerimaJpaEntity> cQuery = cBuilder.createQuery(ParameterStatusKependudukanPenerimaJpaEntity.class);
        Root<ParameterStatusKependudukanPenerimaJpaEntity> root = cQuery.from(ParameterStatusKependudukanPenerimaJpaEntity.class);

        // Filter and Sorting
        cQuery.where(ParameterStatusKependudukanPenerimaPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ParameterStatusKependudukanPenerimaJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ParameterStatusKependudukanPenerimaJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ParameterStatusKependudukanPenerima> list = resultList.stream()
                .map(ParameterStatusKependudukanPenerimaMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ParameterStatusKependudukanPenerimaJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ParameterStatusKependudukanPenerimaPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ParameterStatusKependudukanPenerima> findFirstById(ParameterStatusKependudukanPenerimaId id) {
        return springDataParameterStatusKependudukanPenerimaJpaRepository
                .findFirstByCode(id.value())
                .map(ParameterStatusKependudukanPenerimaMapper::toDomain);
    }

    @Override
    public Optional<ParameterStatusKependudukanPenerima> findFirstByIdAndAuditDeletedAtIsNull(ParameterStatusKependudukanPenerimaId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterStatusKependudukanPenerimaJpaEntity> cQuery = cBuilder.createQuery(ParameterStatusKependudukanPenerimaJpaEntity.class);
        Root<ParameterStatusKependudukanPenerimaJpaEntity> root = cQuery.from(ParameterStatusKependudukanPenerimaJpaEntity.class);

        // Filter Primary Key
        cQuery.where(ParameterStatusKependudukanPenerimaPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ParameterStatusKependudukanPenerimaJpaEntity> query = entityManager.createQuery(cQuery);
        List<ParameterStatusKependudukanPenerimaJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ParameterStatusKependudukanPenerimaMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<ParameterStatusKependudukanPenerima> findFirstByCode(ParameterStatusKependudukanPenerimaCode code) {
        return springDataParameterStatusKependudukanPenerimaJpaRepository
                .findFirstByCode(code.value())
                .map(ParameterStatusKependudukanPenerimaMapper::toDomain);
    }

}
