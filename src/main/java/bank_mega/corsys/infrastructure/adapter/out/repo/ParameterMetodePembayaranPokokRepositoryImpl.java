package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokok;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokCode;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokId;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranPokokRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterMetodePembayaranPokokJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataParameterMetodePembayaranPokokJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ParameterMetodePembayaranPokokMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ParameterMetodePembayaranPokokPredicate;
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
public class ParameterMetodePembayaranPokokRepositoryImpl implements ParameterMetodePembayaranPokokRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "value"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataParameterMetodePembayaranPokokJpaRepository springDataParameterMetodePembayaranPokokJpaRepository;

    @Override
    public ParameterMetodePembayaranPokok save(ParameterMetodePembayaranPokok parameterMetodePembayaranPokok) {
        return ParameterMetodePembayaranPokokMapper.toDomain(springDataParameterMetodePembayaranPokokJpaRepository.save(
                ParameterMetodePembayaranPokokMapper.toJpaEntity(parameterMetodePembayaranPokok)
        ));
    }

    @Override
    public void delete(ParameterMetodePembayaranPokok parameterMetodePembayaranPokok) {
        springDataParameterMetodePembayaranPokokJpaRepository.delete(ParameterMetodePembayaranPokokMapper.toJpaEntity(parameterMetodePembayaranPokok));
    }

    @Override
    public long count() {
        return springDataParameterMetodePembayaranPokokJpaRepository.count();
    }

    @Override
    public Page<@NonNull ParameterMetodePembayaranPokok> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterMetodePembayaranPokokJpaEntity> cQuery = cBuilder.createQuery(ParameterMetodePembayaranPokokJpaEntity.class);
        Root<ParameterMetodePembayaranPokokJpaEntity> root = cQuery.from(ParameterMetodePembayaranPokokJpaEntity.class);

        // Filter and Sorting
        cQuery.where(ParameterMetodePembayaranPokokPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ParameterMetodePembayaranPokokJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ParameterMetodePembayaranPokokJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ParameterMetodePembayaranPokok> list = resultList.stream()
                .map(ParameterMetodePembayaranPokokMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ParameterMetodePembayaranPokokJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ParameterMetodePembayaranPokokPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ParameterMetodePembayaranPokok> findFirstById(ParameterMetodePembayaranPokokId id) {
        return springDataParameterMetodePembayaranPokokJpaRepository
                .findFirstByCode(id.value())
                .map(ParameterMetodePembayaranPokokMapper::toDomain);
    }

    @Override
    public Optional<ParameterMetodePembayaranPokok> findFirstByIdAndAuditDeletedAtIsNull(ParameterMetodePembayaranPokokId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterMetodePembayaranPokokJpaEntity> cQuery = cBuilder.createQuery(ParameterMetodePembayaranPokokJpaEntity.class);
        Root<ParameterMetodePembayaranPokokJpaEntity> root = cQuery.from(ParameterMetodePembayaranPokokJpaEntity.class);

        // Filter Primary Key
        cQuery.where(ParameterMetodePembayaranPokokPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ParameterMetodePembayaranPokokJpaEntity> query = entityManager.createQuery(cQuery);
        List<ParameterMetodePembayaranPokokJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ParameterMetodePembayaranPokokMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<ParameterMetodePembayaranPokok> findFirstByCode(ParameterMetodePembayaranPokokCode code) {
        return springDataParameterMetodePembayaranPokokJpaRepository
                .findFirstByCode(code.value())
                .map(ParameterMetodePembayaranPokokMapper::toDomain);
    }

}
