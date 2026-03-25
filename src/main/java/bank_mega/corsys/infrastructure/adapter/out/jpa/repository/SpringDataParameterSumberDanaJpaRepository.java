package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterSumberDanaJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataParameterSumberDanaJpaRepository extends JpaRepository<ParameterSumberDanaJpaEntity, String> {

    Optional<ParameterSumberDanaJpaEntity> findFirstByCodeAndAuditDeletedAtIsNull(String code);

    Optional<ParameterSumberDanaJpaEntity> findFirstByCode(String code);

    Page<ParameterSumberDanaJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
