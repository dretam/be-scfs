package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisTransaksiRtgsJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataParameterJenisTransaksiRtgsJpaRepository extends JpaRepository<ParameterJenisTransaksiRtgsJpaEntity, Integer> {

    Optional<ParameterJenisTransaksiRtgsJpaEntity> findFirstByCodeAndAuditDeletedAtIsNull(Integer code);

    Optional<ParameterJenisTransaksiRtgsJpaEntity> findFirstByCode(Integer code);

    Page<ParameterJenisTransaksiRtgsJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
