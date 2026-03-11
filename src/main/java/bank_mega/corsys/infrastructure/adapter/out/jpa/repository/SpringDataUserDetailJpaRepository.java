package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserDetailJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUserDetailJpaRepository extends JpaRepository<@NonNull UserDetailJpaEntity, @NonNull Long>, PagingAndSortingRepository<@NonNull UserDetailJpaEntity, @NonNull Long> {

    Optional<UserDetailJpaEntity> findFirstById(Long id);

    Optional<UserDetailJpaEntity> findFirstByIdAndAuditDeletedAtIsNull(Long id);

    Optional<UserDetailJpaEntity> findFirstByUserIdAndAuditDeletedAtIsNull(UserJpaEntity userId);

}
