package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisPerpanjanganJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataParameterJenisPerpanjanganJpaRepository extends JpaRepository<ParameterJenisPerpanjanganJpaEntity, String> {

    Optional<ParameterJenisPerpanjanganJpaEntity> findFirstByCodeAndAuditDeletedAtIsNull(String code);

    Optional<ParameterJenisPerpanjanganJpaEntity> findFirstByCode(String code);

    Page<ParameterJenisPerpanjanganJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
