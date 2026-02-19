package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.OCRDataJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataOCRDataJpaRepository
        extends JpaRepository<OCRDataJpaEntity, Long> {

    Optional<OCRDataJpaEntity> findFirstById(Long id);

    Optional<OCRDataJpaEntity> findFirstByIdAndAuditDeletedAtIsNull(Long id);
}