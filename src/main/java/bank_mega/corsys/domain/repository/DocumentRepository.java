package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.document.Document;
import bank_mega.corsys.domain.model.common.Id;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface DocumentRepository {

    Document save(Document document);

    void delete(Document document);

    long count();

    Page<@NonNull Document> findAllPageable(int page, int size, Set<String> expand, String sort, String filter);

    Optional<Document> findFirstById(Id id);

    Optional<Document> findFirstByIdAndAuditDeletedAtIsNull(Id id);

    Optional<Document> findFirstByIdAndAuditDeletedAtIsNull(Id id, Set<String> expands);

    Optional<Document> findFirstByFilenameAndAuditDeletedAtIsNull(String filename);
    
}