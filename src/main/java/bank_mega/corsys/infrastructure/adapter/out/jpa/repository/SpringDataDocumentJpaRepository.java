package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.DocumentJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataDocumentJpaRepository extends JpaRepository<@NonNull DocumentJpaEntity, @NonNull Long>, PagingAndSortingRepository<@NonNull DocumentJpaEntity, @NonNull Long> {

    long count();

    Optional<DocumentJpaEntity> findFirstById(Long id);

    Optional<DocumentJpaEntity> findFirstByAudit_CreatedBy(Long uploadedBy);

    Optional<DocumentJpaEntity> findFirstByIdAndAuditDeletedAtIsNull(Long id);

    Optional<DocumentJpaEntity> findFirstByAudit_CreatedByAndAuditDeletedAtIsNull(Long uploadedBy);

    Optional<DocumentJpaEntity> findFirstByFilenameAndAuditDeletedAtIsNull(String filename);

    Optional<DocumentJpaEntity> findFirstByFilename(String filename);

    Optional<DocumentJpaEntity> findFirstByFilenameAndIdNot(String filename, Long id);

}