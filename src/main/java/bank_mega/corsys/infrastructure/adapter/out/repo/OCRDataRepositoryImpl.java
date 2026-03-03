package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.repository.OCRDataRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.OCRDataJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataOCRDataJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.OCRDataMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.OCRDataPredicate;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import bank_mega.corsys.infrastructure.util.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class OCRDataRepositoryImpl implements OCRDataRepository {
    private final SpringDataOCRDataJpaRepository springDataRepository;
    private final EntityManager entityManager;

    private final String[] availableSort = {
            "id",
            "atasNama",
            "nominal",
            "status",
            "audit.createdAt"
    };
    @Override
    public OCRData save(OCRData ocrData) {
        return OCRDataMapper.toDomain(
                springDataRepository.save(
                        OCRDataMapper.toJpaEntity(ocrData)
                )
        );
    }

    @Override
    public Optional<OCRData> findFirstById(Long id) {
        return springDataRepository
                .findFirstById(id)
                .map(OCRDataMapper::toDomain);
    }

    @Override
    public void delete(OCRData ocrData) {
        springDataRepository.delete(
                OCRDataMapper.toJpaEntity(ocrData)
        );
    }

    @Override
    public List<OCRData> saveAll(List<OCRData> ocrDataList) {

        List<OCRDataJpaEntity> entities = ocrDataList.stream()
                .map(OCRDataMapper::toJpaEntity)
                .toList();

        return springDataRepository
                .saveAll(entities)
                .stream()
                .map(OCRDataMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<OCRData> findFirstByIdAndAuditDeletedAtIsNull(Long id) {
        return springDataRepository
                .findFirstByIdAndAuditDeletedAtIsNull(id)
                .map(OCRDataMapper::toDomain);
    }

    @Override
    public Page<OCRData> findAllPageable(
            int page,
            int size,
            Set<String> expands,
            String sort,
            String filter
    ) {

        int pageIndex = page - 1;

        Sort sortBy = ParserUtil.sortParse(
                this.availableSort,
                sort,
                this.availableSort[0]
        );

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OCRDataJpaEntity> cq =
                cb.createQuery(OCRDataJpaEntity.class);

        Root<OCRDataJpaEntity> root =
                cq.from(OCRDataJpaEntity.class);

        // Expands fetching
        if (expands.contains("role")) root.fetch("role", JoinType.LEFT);

        cq.where(
                OCRDataPredicate.listBuild(cb, root, filter)
        );

        cq.orderBy(
                ParserUtil.toOrders(sortBy, cb, root)
        );

        TypedQuery<OCRDataJpaEntity> query =
                entityManager.createQuery(cq);

        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);

        List<OCRData> list = query.getResultList()
                .stream()
                .map(OCRDataMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cb,
                        OCRDataJpaEntity.class,
                        (cbCount, rootCount) -> List.of(
                                OCRDataPredicate.listBuild(
                                        cbCount,
                                        rootCount,
                                        filter
                                )
                        )
                )
        );
    }

}
