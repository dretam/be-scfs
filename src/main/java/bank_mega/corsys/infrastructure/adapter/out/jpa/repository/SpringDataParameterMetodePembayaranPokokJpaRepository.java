package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterMetodePembayaranPokokJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataParameterMetodePembayaranPokokJpaRepository extends JpaRepository<ParameterMetodePembayaranPokokJpaEntity, String> {

    Optional<ParameterMetodePembayaranPokokJpaEntity> findFirstByCodeAndAuditDeletedAtIsNull(String code);

    Optional<ParameterMetodePembayaranPokokJpaEntity> findFirstByCode(String code);

    Page<ParameterMetodePembayaranPokokJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
