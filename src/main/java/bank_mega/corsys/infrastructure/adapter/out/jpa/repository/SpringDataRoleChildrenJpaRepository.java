package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleChildrenJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataRoleChildrenJpaRepository extends JpaRepository<@NonNull RoleChildrenJpaEntity, @NonNull String>,
        PagingAndSortingRepository<@NonNull RoleChildrenJpaEntity, @NonNull String> {

    long count();

    Optional<RoleChildrenJpaEntity> findFirstByCode(String code);

    Optional<RoleChildrenJpaEntity> findFirstByName(String name);

    List<@NonNull RoleChildrenJpaEntity> findAllByAuditDeletedAtIsNull();

    @Query("SELECT r FROM RoleChildrenJpaEntity r LEFT JOIN FETCH r.permissions WHERE r.name = :name")
    Optional<RoleChildrenJpaEntity> findFirstByNameWithFetch(@Param("name") String name);
}
