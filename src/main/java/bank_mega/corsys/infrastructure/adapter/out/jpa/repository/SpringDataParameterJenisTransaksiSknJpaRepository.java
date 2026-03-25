package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisTransaksiSknJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataParameterJenisTransaksiSknJpaRepository extends JpaRepository<ParameterJenisTransaksiSknJpaEntity, Integer> {

    Optional<ParameterJenisTransaksiSknJpaEntity> findFirstByCodeAndAuditDeletedAtIsNull(Integer code);

    Optional<ParameterJenisTransaksiSknJpaEntity> findFirstByCode(Integer code);

    Page<ParameterJenisTransaksiSknJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
