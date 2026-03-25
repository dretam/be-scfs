package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgs;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsCode;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsId;
import bank_mega.corsys.domain.repository.ParameterJenisTransaksiRtgsRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisTransaksiRtgsJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataParameterJenisTransaksiRtgsJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ParameterJenisTransaksiRtgsMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ParameterJenisTransaksiRtgsPredicate;
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
public class ParameterJenisTransaksiRtgsRepositoryImpl implements ParameterJenisTransaksiRtgsRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "value"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataParameterJenisTransaksiRtgsJpaRepository springDataParameterJenisTransaksiRtgsJpaRepository;

    @Override
    public ParameterJenisTransaksiRtgs save(ParameterJenisTransaksiRtgs parameterJenisTransaksiRtgs) {
        return ParameterJenisTransaksiRtgsMapper.toDomain(springDataParameterJenisTransaksiRtgsJpaRepository.save(
                ParameterJenisTransaksiRtgsMapper.toJpaEntity(parameterJenisTransaksiRtgs)
        ));
    }

    @Override
    public void delete(ParameterJenisTransaksiRtgs parameterJenisTransaksiRtgs) {
        springDataParameterJenisTransaksiRtgsJpaRepository.delete(ParameterJenisTransaksiRtgsMapper.toJpaEntity(parameterJenisTransaksiRtgs));
    }

    @Override
    public long count() {
        return springDataParameterJenisTransaksiRtgsJpaRepository.count();
    }

    @Override
    public Page<@NonNull ParameterJenisTransaksiRtgs> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterJenisTransaksiRtgsJpaEntity> cQuery = cBuilder.createQuery(ParameterJenisTransaksiRtgsJpaEntity.class);
        Root<ParameterJenisTransaksiRtgsJpaEntity> root = cQuery.from(ParameterJenisTransaksiRtgsJpaEntity.class);

        // Filter and Sorting
        cQuery.where(ParameterJenisTransaksiRtgsPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ParameterJenisTransaksiRtgsJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ParameterJenisTransaksiRtgsJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ParameterJenisTransaksiRtgs> list = resultList.stream()
                .map(ParameterJenisTransaksiRtgsMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ParameterJenisTransaksiRtgsJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ParameterJenisTransaksiRtgsPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ParameterJenisTransaksiRtgs> findFirstById(ParameterJenisTransaksiRtgsId id) {
        return springDataParameterJenisTransaksiRtgsJpaRepository
                .findFirstByCode(id.value())
                .map(ParameterJenisTransaksiRtgsMapper::toDomain);
    }

    @Override
    public Optional<ParameterJenisTransaksiRtgs> findFirstByIdAndAuditDeletedAtIsNull(ParameterJenisTransaksiRtgsId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterJenisTransaksiRtgsJpaEntity> cQuery = cBuilder.createQuery(ParameterJenisTransaksiRtgsJpaEntity.class);
        Root<ParameterJenisTransaksiRtgsJpaEntity> root = cQuery.from(ParameterJenisTransaksiRtgsJpaEntity.class);

        // Filter Primary Key
        cQuery.where(ParameterJenisTransaksiRtgsPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ParameterJenisTransaksiRtgsJpaEntity> query = entityManager.createQuery(cQuery);
        List<ParameterJenisTransaksiRtgsJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ParameterJenisTransaksiRtgsMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<ParameterJenisTransaksiRtgs> findFirstByCode(ParameterJenisTransaksiRtgsCode code) {
        return springDataParameterJenisTransaksiRtgsJpaRepository
                .findFirstByCode(code.value())
                .map(ParameterJenisTransaksiRtgsMapper::toDomain);
    }

}
