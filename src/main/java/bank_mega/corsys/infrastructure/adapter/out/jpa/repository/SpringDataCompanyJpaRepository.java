package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.CompanyJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataCompanyJpaRepository extends JpaRepository<@NonNull CompanyJpaEntity, @NonNull UUID>,
        PagingAndSortingRepository<@NonNull CompanyJpaEntity, @NonNull UUID> {

    long count();

    Optional<CompanyJpaEntity> findFirstById(UUID id);

    Optional<CompanyJpaEntity> findFirstByCif(String cif);

}
