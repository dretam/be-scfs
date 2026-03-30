package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.CompanyJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataCompanyJpaRepository extends JpaRepository<@NonNull CompanyJpaEntity, @NonNull String>,
        PagingAndSortingRepository<@NonNull CompanyJpaEntity, @NonNull String> {

    long count();

    Optional<CompanyJpaEntity> findFirstById(String id);

    Optional<CompanyJpaEntity> findFirstByCif(String cif);

}
