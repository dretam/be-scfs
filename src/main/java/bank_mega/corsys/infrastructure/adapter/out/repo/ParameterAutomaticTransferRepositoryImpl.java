package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransfer;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferCode;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferId;
import bank_mega.corsys.domain.repository.ParameterAutomaticTransferRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterAutomaticTransferJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataParameterAutomaticTransferJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ParameterAutomaticTransferMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ParameterAutomaticTransferPredicate;
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
public class ParameterAutomaticTransferRepositoryImpl implements ParameterAutomaticTransferRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "value"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataParameterAutomaticTransferJpaRepository springDataParameterAutomaticTransferJpaRepository;

    @Override
    public ParameterAutomaticTransfer save(ParameterAutomaticTransfer parameterAutomaticTransfer) {
        return ParameterAutomaticTransferMapper.toDomain(springDataParameterAutomaticTransferJpaRepository.save(
                ParameterAutomaticTransferMapper.toJpaEntity(parameterAutomaticTransfer)
        ));
    }

    @Override
    public void delete(ParameterAutomaticTransfer parameterAutomaticTransfer) {
        springDataParameterAutomaticTransferJpaRepository.delete(ParameterAutomaticTransferMapper.toJpaEntity(parameterAutomaticTransfer));
    }

    @Override
    public long count() {
        return springDataParameterAutomaticTransferJpaRepository.count();
    }

    @Override
    public Page<@NonNull ParameterAutomaticTransfer> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterAutomaticTransferJpaEntity> cQuery = cBuilder.createQuery(ParameterAutomaticTransferJpaEntity.class);
        Root<ParameterAutomaticTransferJpaEntity> root = cQuery.from(ParameterAutomaticTransferJpaEntity.class);

        // Filter and Sorting
        cQuery.where(ParameterAutomaticTransferPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ParameterAutomaticTransferJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ParameterAutomaticTransferJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ParameterAutomaticTransfer> list = resultList.stream()
                .map(ParameterAutomaticTransferMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ParameterAutomaticTransferJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ParameterAutomaticTransferPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ParameterAutomaticTransfer> findFirstById(ParameterAutomaticTransferId id) {
        return springDataParameterAutomaticTransferJpaRepository
                .findFirstByCode(id.value())
                .map(ParameterAutomaticTransferMapper::toDomain);
    }

    @Override
    public Optional<ParameterAutomaticTransfer> findFirstByIdAndAuditDeletedAtIsNull(ParameterAutomaticTransferId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterAutomaticTransferJpaEntity> cQuery = cBuilder.createQuery(ParameterAutomaticTransferJpaEntity.class);
        Root<ParameterAutomaticTransferJpaEntity> root = cQuery.from(ParameterAutomaticTransferJpaEntity.class);

        // Filter Primary Key
        cQuery.where(ParameterAutomaticTransferPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ParameterAutomaticTransferJpaEntity> query = entityManager.createQuery(cQuery);
        List<ParameterAutomaticTransferJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ParameterAutomaticTransferMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<ParameterAutomaticTransfer> findFirstByCode(ParameterAutomaticTransferCode code) {
        return springDataParameterAutomaticTransferJpaRepository
                .findFirstByCode(code.value())
                .map(ParameterAutomaticTransferMapper::toDomain);
    }

}
