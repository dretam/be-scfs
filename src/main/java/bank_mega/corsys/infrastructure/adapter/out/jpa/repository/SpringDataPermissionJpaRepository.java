package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.PermissionJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataPermissionJpaRepository extends JpaRepository<PermissionJpaEntity, Long> {

    Optional<PermissionJpaEntity> findFirstByCode(String code);

    Optional<PermissionJpaEntity> findFirstByIdAndAuditDeletedAtIsNull(Long id);

    Optional<PermissionJpaEntity> findFirstById(Long id);

    @Query("SELECT p FROM RoleJpaEntity r JOIN r.permissions p WHERE r.id = :roleId")
    List<PermissionJpaEntity> findAllByRoleId(@Param("roleId") String roleId);

    Page<PermissionJpaEntity> findAllByAuditDeletedAtIsNull(Pageable pageable);

}
