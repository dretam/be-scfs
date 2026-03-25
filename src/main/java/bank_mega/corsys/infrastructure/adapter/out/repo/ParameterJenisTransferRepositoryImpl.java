package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransfer;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferCode;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferId;
import bank_mega.corsys.domain.repository.ParameterJenisTransferRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisTransferJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataParameterJenisTransferJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ParameterJenisTransferMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ParameterJenisTransferPredicate;
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
public class ParameterJenisTransferRepositoryImpl implements ParameterJenisTransferRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "value"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataParameterJenisTransferJpaRepository springDataParameterJenisTransferJpaRepository;

    @Override
    public ParameterJenisTransfer save(ParameterJenisTransfer parameterJenisTransfer) {
        return ParameterJenisTransferMapper.toDomain(springDataParameterJenisTransferJpaRepository.save(
                ParameterJenisTransferMapper.toJpaEntity(parameterJenisTransfer)
        ));
    }

    @Override
    public void delete(ParameterJenisTransfer parameterJenisTransfer) {
        springDataParameterJenisTransferJpaRepository.delete(ParameterJenisTransferMapper.toJpaEntity(parameterJenisTransfer));
    }

    @Override
    public long count() {
        return springDataParameterJenisTransferJpaRepository.count();
    }

    @Override
    public Page<@NonNull ParameterJenisTransfer> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterJenisTransferJpaEntity> cQuery = cBuilder.createQuery(ParameterJenisTransferJpaEntity.class);
        Root<ParameterJenisTransferJpaEntity> root = cQuery.from(ParameterJenisTransferJpaEntity.class);

        // Filter and Sorting
        cQuery.where(ParameterJenisTransferPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ParameterJenisTransferJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ParameterJenisTransferJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ParameterJenisTransfer> list = resultList.stream()
                .map(ParameterJenisTransferMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ParameterJenisTransferJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ParameterJenisTransferPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ParameterJenisTransfer> findFirstById(ParameterJenisTransferId id) {
        return springDataParameterJenisTransferJpaRepository
                .findFirstByCode(id.value())
                .map(ParameterJenisTransferMapper::toDomain);
    }

    @Override
    public Optional<ParameterJenisTransfer> findFirstByIdAndAuditDeletedAtIsNull(ParameterJenisTransferId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterJenisTransferJpaEntity> cQuery = cBuilder.createQuery(ParameterJenisTransferJpaEntity.class);
        Root<ParameterJenisTransferJpaEntity> root = cQuery.from(ParameterJenisTransferJpaEntity.class);

        // Filter Primary Key
        cQuery.where(ParameterJenisTransferPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ParameterJenisTransferJpaEntity> query = entityManager.createQuery(cQuery);
        List<ParameterJenisTransferJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ParameterJenisTransferMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<ParameterJenisTransfer> findFirstByCode(ParameterJenisTransferCode code) {
        return springDataParameterJenisTransferJpaRepository
                .findFirstByCode(code.value())
                .map(ParameterJenisTransferMapper::toDomain);
    }

}
