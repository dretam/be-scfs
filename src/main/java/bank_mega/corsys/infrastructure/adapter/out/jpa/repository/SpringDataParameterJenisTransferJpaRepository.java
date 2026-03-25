package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisTransferJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataParameterJenisTransferJpaRepository extends JpaRepository<ParameterJenisTransferJpaEntity, Integer> {

    Optional<ParameterJenisTransferJpaEntity> findFirstByCodeAndAuditDeletedAtIsNull(Integer code);

    Optional<ParameterJenisTransferJpaEntity> findFirstByCode(Integer code);

    Page<ParameterJenisTransferJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
