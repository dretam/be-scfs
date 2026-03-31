package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.AccessLogJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataAccessLogJpaRepository extends JpaRepository<@NonNull AccessLogJpaEntity, @NonNull UUID>, PagingAndSortingRepository<@NonNull AccessLogJpaEntity, @NonNull UUID> {

    long count();

    void deleteByCreatedAtLessThanEqual(Instant createdAt);

    Optional<AccessLogJpaEntity> findFirstById(UUID id);

}
