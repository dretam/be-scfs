package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterMetodePembayaranBungaJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataParameterMetodePembayaranBungaJpaRepository extends JpaRepository<ParameterMetodePembayaranBungaJpaEntity, String> {

    Optional<ParameterMetodePembayaranBungaJpaEntity> findFirstByCodeAndAuditDeletedAtIsNull(String code);

    Optional<ParameterMetodePembayaranBungaJpaEntity> findFirstByCode(String code);

    Page<ParameterMetodePembayaranBungaJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
