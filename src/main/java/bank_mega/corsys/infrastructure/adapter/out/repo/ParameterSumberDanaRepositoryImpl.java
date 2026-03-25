package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDana;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaCode;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaId;
import bank_mega.corsys.domain.repository.ParameterSumberDanaRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterSumberDanaJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataParameterSumberDanaJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ParameterSumberDanaMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.ParameterSumberDanaPredicate;
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
public class ParameterSumberDanaRepositoryImpl implements ParameterSumberDanaRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "value"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataParameterSumberDanaJpaRepository springDataParameterSumberDanaJpaRepository;

    @Override
    public ParameterSumberDana save(ParameterSumberDana parameterSumberDana) {
        return ParameterSumberDanaMapper.toDomain(springDataParameterSumberDanaJpaRepository.save(
                ParameterSumberDanaMapper.toJpaEntity(parameterSumberDana)
        ));
    }

    @Override
    public void delete(ParameterSumberDana parameterSumberDana) {
        springDataParameterSumberDanaJpaRepository.delete(ParameterSumberDanaMapper.toJpaEntity(parameterSumberDana));
    }

    @Override
    public long count() {
        return springDataParameterSumberDanaJpaRepository.count();
    }

    @Override
    public Page<@NonNull ParameterSumberDana> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterSumberDanaJpaEntity> cQuery = cBuilder.createQuery(ParameterSumberDanaJpaEntity.class);
        Root<ParameterSumberDanaJpaEntity> root = cQuery.from(ParameterSumberDanaJpaEntity.class);

        // Filter and Sorting
        cQuery.where(ParameterSumberDanaPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<ParameterSumberDanaJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<ParameterSumberDanaJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<ParameterSumberDana> list = resultList.stream()
                .map(ParameterSumberDanaMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        ParameterSumberDanaJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                ParameterSumberDanaPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<ParameterSumberDana> findFirstById(ParameterSumberDanaId id) {
        return springDataParameterSumberDanaJpaRepository
                .findFirstByCode(id.value())
                .map(ParameterSumberDanaMapper::toDomain);
    }

    @Override
    public Optional<ParameterSumberDana> findFirstByIdAndAuditDeletedAtIsNull(ParameterSumberDanaId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ParameterSumberDanaJpaEntity> cQuery = cBuilder.createQuery(ParameterSumberDanaJpaEntity.class);
        Root<ParameterSumberDanaJpaEntity> root = cQuery.from(ParameterSumberDanaJpaEntity.class);

        // Filter Primary Key
        cQuery.where(ParameterSumberDanaPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<ParameterSumberDanaJpaEntity> query = entityManager.createQuery(cQuery);
        List<ParameterSumberDanaJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(ParameterSumberDanaMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<ParameterSumberDana> findFirstByCode(ParameterSumberDanaCode code) {
        return springDataParameterSumberDanaJpaRepository
                .findFirstByCode(code.value())
                .map(ParameterSumberDanaMapper::toDomain);
    }

}
