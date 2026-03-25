package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterApproverBiayaMateraiJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataParameterApproverBiayaMateraiJpaRepository extends JpaRepository<ParameterApproverBiayaMateraiJpaEntity, String> {

    Optional<ParameterApproverBiayaMateraiJpaEntity> findFirstByCodeAndAuditDeletedAtIsNull(String code);

    Optional<ParameterApproverBiayaMateraiJpaEntity> findFirstByCode(String code);

    Page<ParameterApproverBiayaMateraiJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
