package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMaterai;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiCode;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiId;
import bank_mega.corsys.domain.repository.ParameterApproverBiayaMateraiRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterApproverBiayaMateraiJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataParameterApproverBiayaMateraiJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ParameterApproverBiayaMateraiMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ParameterApproverBiayaMateraiPredicate;
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
public class ParameterApproverBiayaMateraiRepositoryImpl implements ParameterApproverBiayaMateraiRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "value"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataParameterApproverBiayaMateraiJpaRepository springDataParameterApproverBiayaMateraiJpaRepository;

    @Override
    public ParameterApproverBiayaMaterai save(ParameterApproverBiayaMaterai parameterApproverBiayaMaterai) {
        return ParameterApproverBiayaMateraiMapper.toDomain(springDataParameterApproverBiayaMateraiJpaRepository.save(
                ParameterApproverBiayaMateraiMapper.toJpaEntity(parameterApproverBiayaMaterai)
        ));
    }

    @Override
    public void delete(ParameterApproverBiayaMaterai parameterApproverBiayaMaterai) {
        springDataParameterApproverBiayaMateraiJpaRepository.delete(ParameterApproverBiayaMateraiMapper.toJpaEntity(parameterApproverBiayaMaterai));
    }

    @Override
    public long count() {
        return springDataParameterApproverBiayaMateraiJpaRepository.count();
    }

    @Override
    public Page<@NonNull ParameterApproverBiayaMaterai> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterApproverBiayaMateraiJpaEntity> cQuery = cBuilder.createQuery(ParameterApproverBiayaMateraiJpaEntity.class);
        Root<ParameterApproverBiayaMateraiJpaEntity> root = cQuery.from(ParameterApproverBiayaMateraiJpaEntity.class);

        // Filter and Sorting
        cQuery.where(ParameterApproverBiayaMateraiPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ParameterApproverBiayaMateraiJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ParameterApproverBiayaMateraiJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ParameterApproverBiayaMaterai> list = resultList.stream()
                .map(ParameterApproverBiayaMateraiMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ParameterApproverBiayaMateraiJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ParameterApproverBiayaMateraiPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ParameterApproverBiayaMaterai> findFirstById(ParameterApproverBiayaMateraiId id) {
        return springDataParameterApproverBiayaMateraiJpaRepository
                .findFirstByCode(id.value())
                .map(ParameterApproverBiayaMateraiMapper::toDomain);
    }

    @Override
    public Optional<ParameterApproverBiayaMaterai> findFirstByIdAndAuditDeletedAtIsNull(ParameterApproverBiayaMateraiId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterApproverBiayaMateraiJpaEntity> cQuery = cBuilder.createQuery(ParameterApproverBiayaMateraiJpaEntity.class);
        Root<ParameterApproverBiayaMateraiJpaEntity> root = cQuery.from(ParameterApproverBiayaMateraiJpaEntity.class);

        // Filter Primary Key
        cQuery.where(ParameterApproverBiayaMateraiPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ParameterApproverBiayaMateraiJpaEntity> query = entityManager.createQuery(cQuery);
        List<ParameterApproverBiayaMateraiJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ParameterApproverBiayaMateraiMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<ParameterApproverBiayaMaterai> findFirstByCode(ParameterApproverBiayaMateraiCode code) {
        return springDataParameterApproverBiayaMateraiJpaRepository
                .findFirstByCode(code.value())
                .map(ParameterApproverBiayaMateraiMapper::toDomain);
    }

}
