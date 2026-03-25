package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisNasabahPenerimaJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataParameterJenisNasabahPenerimaJpaRepository extends JpaRepository<ParameterJenisNasabahPenerimaJpaEntity, Integer> {

    Optional<ParameterJenisNasabahPenerimaJpaEntity> findFirstByCodeAndAuditDeletedAtIsNull(Integer code);

    Optional<ParameterJenisNasabahPenerimaJpaEntity> findFirstByCode(Integer code);

    Page<ParameterJenisNasabahPenerimaJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
