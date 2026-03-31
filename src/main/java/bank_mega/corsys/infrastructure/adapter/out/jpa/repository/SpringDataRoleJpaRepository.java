package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataRoleJpaRepository extends JpaRepository<@NonNull RoleJpaEntity, @NonNull String>,
        PagingAndSortingRepository<@NonNull RoleJpaEntity, @NonNull String> {

    long count();

    Optional<RoleJpaEntity> findFirstByCode(String code);

    Optional<RoleJpaEntity> findFirstByName(String name);

    @Query("SELECT r FROM RoleJpaEntity r LEFT JOIN FETCH r.permissions WHERE r.name = :name")
    Optional<RoleJpaEntity> findFirstByNameWithFetch(@Param("name") String name);
}
