package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerima;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaCode;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaId;
import bank_mega.corsys.domain.repository.ParameterJenisNasabahPenerimaRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisNasabahPenerimaJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataParameterJenisNasabahPenerimaJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ParameterJenisNasabahPenerimaMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ParameterJenisNasabahPenerimaPredicate;
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
public class ParameterJenisNasabahPenerimaRepositoryImpl implements ParameterJenisNasabahPenerimaRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "value"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataParameterJenisNasabahPenerimaJpaRepository springDataParameterJenisNasabahPenerimaJpaRepository;

    @Override
    public ParameterJenisNasabahPenerima save(ParameterJenisNasabahPenerima parameterJenisNasabahPenerima) {
        return ParameterJenisNasabahPenerimaMapper.toDomain(springDataParameterJenisNasabahPenerimaJpaRepository.save(
                ParameterJenisNasabahPenerimaMapper.toJpaEntity(parameterJenisNasabahPenerima)
        ));
    }

    @Override
    public void delete(ParameterJenisNasabahPenerima parameterJenisNasabahPenerima) {
        springDataParameterJenisNasabahPenerimaJpaRepository.delete(ParameterJenisNasabahPenerimaMapper.toJpaEntity(parameterJenisNasabahPenerima));
    }

    @Override
    public long count() {
        return springDataParameterJenisNasabahPenerimaJpaRepository.count();
    }

    @Override
    public Page<@NonNull ParameterJenisNasabahPenerima> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterJenisNasabahPenerimaJpaEntity> cQuery = cBuilder.createQuery(ParameterJenisNasabahPenerimaJpaEntity.class);
        Root<ParameterJenisNasabahPenerimaJpaEntity> root = cQuery.from(ParameterJenisNasabahPenerimaJpaEntity.class);

        // Filter and Sorting
        cQuery.where(ParameterJenisNasabahPenerimaPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ParameterJenisNasabahPenerimaJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ParameterJenisNasabahPenerimaJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ParameterJenisNasabahPenerima> list = resultList.stream()
                .map(ParameterJenisNasabahPenerimaMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ParameterJenisNasabahPenerimaJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ParameterJenisNasabahPenerimaPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ParameterJenisNasabahPenerima> findFirstById(ParameterJenisNasabahPenerimaId id) {
        return springDataParameterJenisNasabahPenerimaJpaRepository
                .findFirstByCode(id.value())
                .map(ParameterJenisNasabahPenerimaMapper::toDomain);
    }

    @Override
    public Optional<ParameterJenisNasabahPenerima> findFirstByIdAndAuditDeletedAtIsNull(ParameterJenisNasabahPenerimaId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterJenisNasabahPenerimaJpaEntity> cQuery = cBuilder.createQuery(ParameterJenisNasabahPenerimaJpaEntity.class);
        Root<ParameterJenisNasabahPenerimaJpaEntity> root = cQuery.from(ParameterJenisNasabahPenerimaJpaEntity.class);

        // Filter Primary Key
        cQuery.where(ParameterJenisNasabahPenerimaPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ParameterJenisNasabahPenerimaJpaEntity> query = entityManager.createQuery(cQuery);
        List<ParameterJenisNasabahPenerimaJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ParameterJenisNasabahPenerimaMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<ParameterJenisNasabahPenerima> findFirstByCode(ParameterJenisNasabahPenerimaCode code) {
        return springDataParameterJenisNasabahPenerimaJpaRepository
                .findFirstByCode(code.value())
                .map(ParameterJenisNasabahPenerimaMapper::toDomain);
    }

}
