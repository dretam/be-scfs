package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserPermissionJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.UserPermissionIdEmbeddable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataUserPermissionJpaRepository extends JpaRepository<UserPermissionJpaEntity, UserPermissionIdEmbeddable> {

    @Query("SELECT up FROM UserPermissionJpaEntity up JOIN FETCH up.permission WHERE up.id.userId = :userId")
    List<UserPermissionJpaEntity> findAllByUserId(@Param("userId") UUID userId);

    @Query("SELECT up FROM UserPermissionJpaEntity up JOIN FETCH up.permission WHERE up.id.userId = :userId AND up.effect = 'ALLOW'")
    List<UserPermissionJpaEntity> findAllAllowByUserId(@Param("userId") UUID userId);

    @Query("SELECT up FROM UserPermissionJpaEntity up JOIN FETCH up.permission WHERE up.id.userId = :userId AND up.effect = 'DENY'")
    List<UserPermissionJpaEntity> findAllDenyByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query("DELETE FROM UserPermissionJpaEntity up WHERE up.id.userId = :userId")
    void deleteAllByUserId(@Param("userId") UUID userId);
}
