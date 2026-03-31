package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataUserJpaRepository extends JpaRepository<@NonNull UserJpaEntity, @NonNull Long>, PagingAndSortingRepository<@NonNull UserJpaEntity, @NonNull Long> {

    long count();

    Optional<UserJpaEntity> findFirstById(UUID id);

    Optional<UserJpaEntity> findFirstByName(String name);

    Optional<UserJpaEntity> findFirstByNameAndIdNot(String name, UUID id);

    Optional<UserJpaEntity> findFirstByEmail(String email);

    Optional<UserJpaEntity> findFirstByEmailAndIdNot(String email, UUID id);

    Optional<UserJpaEntity> findFirstByIdAndAuditDeletedAtIsNull(UUID id);

    Optional<UserJpaEntity> findFirstByEmailAndAuditDeletedAtIsNull(String email);

    Optional<UserJpaEntity> findFirstByNameAndAuditDeletedAtIsNull(String name);

}
