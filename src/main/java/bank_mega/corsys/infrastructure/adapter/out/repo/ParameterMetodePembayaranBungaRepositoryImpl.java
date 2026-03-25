package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBunga;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaCode;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaId;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranBungaRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterMetodePembayaranBungaJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataParameterMetodePembayaranBungaJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ParameterMetodePembayaranBungaMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ParameterMetodePembayaranBungaPredicate;
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
public class ParameterMetodePembayaranBungaRepositoryImpl implements ParameterMetodePembayaranBungaRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "value"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataParameterMetodePembayaranBungaJpaRepository springDataParameterMetodePembayaranBungaJpaRepository;

    @Override
    public ParameterMetodePembayaranBunga save(ParameterMetodePembayaranBunga parameterMetodePembayaranBunga) {
        return ParameterMetodePembayaranBungaMapper.toDomain(springDataParameterMetodePembayaranBungaJpaRepository.save(
                ParameterMetodePembayaranBungaMapper.toJpaEntity(parameterMetodePembayaranBunga)
        ));
    }

    @Override
    public void delete(ParameterMetodePembayaranBunga parameterMetodePembayaranBunga) {
        springDataParameterMetodePembayaranBungaJpaRepository.delete(ParameterMetodePembayaranBungaMapper.toJpaEntity(parameterMetodePembayaranBunga));
    }

    @Override
    public long count() {
        return springDataParameterMetodePembayaranBungaJpaRepository.count();
    }

    @Override
    public Page<@NonNull ParameterMetodePembayaranBunga> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterMetodePembayaranBungaJpaEntity> cQuery = cBuilder.createQuery(ParameterMetodePembayaranBungaJpaEntity.class);
        Root<ParameterMetodePembayaranBungaJpaEntity> root = cQuery.from(ParameterMetodePembayaranBungaJpaEntity.class);

        // Filter and Sorting
        cQuery.where(ParameterMetodePembayaranBungaPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ParameterMetodePembayaranBungaJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ParameterMetodePembayaranBungaJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ParameterMetodePembayaranBunga> list = resultList.stream()
                .map(ParameterMetodePembayaranBungaMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ParameterMetodePembayaranBungaJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ParameterMetodePembayaranBungaPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ParameterMetodePembayaranBunga> findFirstById(ParameterMetodePembayaranBungaId id) {
        return springDataParameterMetodePembayaranBungaJpaRepository
                .findFirstByCode(id.value())
                .map(ParameterMetodePembayaranBungaMapper::toDomain);
    }

    @Override
    public Optional<ParameterMetodePembayaranBunga> findFirstByIdAndAuditDeletedAtIsNull(ParameterMetodePembayaranBungaId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterMetodePembayaranBungaJpaEntity> cQuery = cBuilder.createQuery(ParameterMetodePembayaranBungaJpaEntity.class);
        Root<ParameterMetodePembayaranBungaJpaEntity> root = cQuery.from(ParameterMetodePembayaranBungaJpaEntity.class);

        // Filter Primary Key
        cQuery.where(ParameterMetodePembayaranBungaPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ParameterMetodePembayaranBungaJpaEntity> query = entityManager.createQuery(cQuery);
        List<ParameterMetodePembayaranBungaJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ParameterMetodePembayaranBungaMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<ParameterMetodePembayaranBunga> findFirstByCode(ParameterMetodePembayaranBungaCode code) {
        return springDataParameterMetodePembayaranBungaJpaRepository
                .findFirstByCode(code.value())
                .map(ParameterMetodePembayaranBungaMapper::toDomain);
    }

}
