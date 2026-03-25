package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterStatusKependudukanPenerimaJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataParameterStatusKependudukanPenerimaJpaRepository extends JpaRepository<ParameterStatusKependudukanPenerimaJpaEntity, Integer> {

    Optional<ParameterStatusKependudukanPenerimaJpaEntity> findFirstByCodeAndAuditDeletedAtIsNull(Integer code);

    Optional<ParameterStatusKependudukanPenerimaJpaEntity> findFirstByCode(Integer code);

    Page<ParameterStatusKependudukanPenerimaJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
