package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.MenuJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataMenuJpaRepository extends JpaRepository<MenuJpaEntity, UUID> {

    Optional<MenuJpaEntity> findFirstByCode(String code);

    Optional<MenuJpaEntity> findFirstByIdAndAuditDeletedAtIsNull(UUID id);

    Optional<MenuJpaEntity> findFirstById(UUID id);

    List<MenuJpaEntity> findAllByParentIdAndAuditDeletedAtIsNull(UUID parentId);
}
