package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.common.Id;
import bank_mega.corsys.domain.model.document.Document;
import bank_mega.corsys.domain.repository.DocumentRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.DocumentJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.mapper.DocumentMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.DocumentPredicate;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataDocumentJpaRepository;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import bank_mega.corsys.infrastructure.util.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class DocumentRepositoryImpl implements DocumentRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "filename",
            "originalName",
            "mimeType"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataDocumentJpaRepository springDataDocumentJpaRepository;

    @Override
    public Document save(Document document) {
        return DocumentMapper.toDomain(springDataDocumentJpaRepository.save(
                DocumentMapper.toJpaEntity(document)
        ));
    }

    @Override
    public void delete(Document document) {
        springDataDocumentJpaRepository.delete(DocumentMapper.toJpaEntity(document));
    }

    @Override
    public long count() {
        return springDataDocumentJpaRepository.count();
    }

    @Override
    public Page<@NonNull Document> findAllPageable(int page, int size, Set<String> expands, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<DocumentJpaEntity> cQuery = cBuilder.createQuery(DocumentJpaEntity.class);
        Root<DocumentJpaEntity> root = cQuery.from(DocumentJpaEntity.class);

        // Expands fetching - for documents we might want to fetch user info later
        // Currently no expansions defined for documents

        // Filter and Sorting
        cQuery.where(DocumentPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<DocumentJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<DocumentJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<Document> list = resultList.stream()
                .map(DocumentMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        DocumentJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                DocumentPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<Document> findFirstById(Id id) {
        return springDataDocumentJpaRepository
                .findFirstById(id.value())
                .map(DocumentMapper::toDomain);
    }

    @Override
    public Optional<Document> findFirstByIdAndAuditDeletedAtIsNull(Id id) {
        return springDataDocumentJpaRepository
                .findFirstByIdAndAuditDeletedAtIsNull(id.value())
                .map(DocumentMapper::toDomain);
    }

    @Override
    public Optional<Document> findFirstByIdAndAuditDeletedAtIsNull(Id id, Set<String> expands) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<DocumentJpaEntity> cQuery = cBuilder.createQuery(DocumentJpaEntity.class);
        Root<DocumentJpaEntity> root = cQuery.from(DocumentJpaEntity.class);

        // Expands fetching - for documents we might want to fetch user info later
        // Currently no expansions defined for documents

        // Filter Primary Key
        cQuery.where(DocumentPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<DocumentJpaEntity> query = entityManager.createQuery(cQuery);
        List<DocumentJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(DocumentMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<Document> findFirstByFilenameAndAuditDeletedAtIsNull(String filename) {
        return springDataDocumentJpaRepository
                .findFirstByFilenameAndAuditDeletedAtIsNull(filename)
                .map(DocumentMapper::toDomain);
    }
}