package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ActivityLogJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataActivityLogJpaRepository extends JpaRepository<@NonNull ActivityLogJpaEntity, @NonNull UUID>, PagingAndSortingRepository<@NonNull ActivityLogJpaEntity, @NonNull UUID> {

    long count();

    void deleteByCreatedAtLessThanEqual(Instant createdAt);

    Optional<ActivityLogJpaEntity> findFirstById(UUID id);

}
