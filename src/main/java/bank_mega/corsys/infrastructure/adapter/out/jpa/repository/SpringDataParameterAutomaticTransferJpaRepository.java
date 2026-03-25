package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterAutomaticTransferJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataParameterAutomaticTransferJpaRepository extends JpaRepository<ParameterAutomaticTransferJpaEntity, String> {

    Optional<ParameterAutomaticTransferJpaEntity> findFirstByCodeAndAuditDeletedAtIsNull(String code);

    Optional<ParameterAutomaticTransferJpaEntity> findFirstByCode(String code);

    Page<ParameterAutomaticTransferJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
