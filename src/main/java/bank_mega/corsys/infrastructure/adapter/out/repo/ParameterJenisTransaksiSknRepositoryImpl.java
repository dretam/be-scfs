package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSkn;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknCode;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknId;
import bank_mega.corsys.domain.repository.ParameterJenisTransaksiSknRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisTransaksiSknJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataParameterJenisTransaksiSknJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ParameterJenisTransaksiSknMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ParameterJenisTransaksiSknPredicate;
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
public class ParameterJenisTransaksiSknRepositoryImpl implements ParameterJenisTransaksiSknRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "value"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataParameterJenisTransaksiSknJpaRepository springDataParameterJenisTransaksiSknJpaRepository;

    @Override
    public ParameterJenisTransaksiSkn save(ParameterJenisTransaksiSkn parameterJenisTransaksiSkn) {
        return ParameterJenisTransaksiSknMapper.toDomain(springDataParameterJenisTransaksiSknJpaRepository.save(
                ParameterJenisTransaksiSknMapper.toJpaEntity(parameterJenisTransaksiSkn)
        ));
    }

    @Override
    public void delete(ParameterJenisTransaksiSkn parameterJenisTransaksiSkn) {
        springDataParameterJenisTransaksiSknJpaRepository.delete(ParameterJenisTransaksiSknMapper.toJpaEntity(parameterJenisTransaksiSkn));
    }

    @Override
    public long count() {
        return springDataParameterJenisTransaksiSknJpaRepository.count();
    }

    @Override
    public Page<@NonNull ParameterJenisTransaksiSkn> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterJenisTransaksiSknJpaEntity> cQuery = cBuilder.createQuery(ParameterJenisTransaksiSknJpaEntity.class);
        Root<ParameterJenisTransaksiSknJpaEntity> root = cQuery.from(ParameterJenisTransaksiSknJpaEntity.class);

        // Filter and Sorting
        cQuery.where(ParameterJenisTransaksiSknPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ParameterJenisTransaksiSknJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ParameterJenisTransaksiSknJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ParameterJenisTransaksiSkn> list = resultList.stream()
                .map(ParameterJenisTransaksiSknMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ParameterJenisTransaksiSknJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ParameterJenisTransaksiSknPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ParameterJenisTransaksiSkn> findFirstById(ParameterJenisTransaksiSknId id) {
        return springDataParameterJenisTransaksiSknJpaRepository
                .findFirstByCode(id.value())
                .map(ParameterJenisTransaksiSknMapper::toDomain);
    }

    @Override
    public Optional<ParameterJenisTransaksiSkn> findFirstByIdAndAuditDeletedAtIsNull(ParameterJenisTransaksiSknId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterJenisTransaksiSknJpaEntity> cQuery = cBuilder.createQuery(ParameterJenisTransaksiSknJpaEntity.class);
        Root<ParameterJenisTransaksiSknJpaEntity> root = cQuery.from(ParameterJenisTransaksiSknJpaEntity.class);

        // Filter Primary Key
        cQuery.where(ParameterJenisTransaksiSknPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ParameterJenisTransaksiSknJpaEntity> query = entityManager.createQuery(cQuery);
        List<ParameterJenisTransaksiSknJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ParameterJenisTransaksiSknMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<ParameterJenisTransaksiSkn> findFirstByCode(ParameterJenisTransaksiSknCode code) {
        return springDataParameterJenisTransaksiSknJpaRepository
                .findFirstByCode(code.value())
                .map(ParameterJenisTransaksiSknMapper::toDomain);
    }

}
