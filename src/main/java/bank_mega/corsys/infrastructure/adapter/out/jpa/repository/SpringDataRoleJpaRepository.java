package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataRoleJpaRepository extends JpaRepository<@NonNull RoleJpaEntity, @NonNull Long>, PagingAndSortingRepository<@NonNull RoleJpaEntity, @NonNull Long> {

    long count();

    Optional<RoleJpaEntity> findFirstById(Long id);

    Optional<RoleJpaEntity> findFirstByName(String name);

}
