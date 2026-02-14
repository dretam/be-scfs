package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.AccessLogJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface SpringDataAccessLogJpaRepository extends JpaRepository<@NonNull AccessLogJpaEntity, @NonNull Long>, PagingAndSortingRepository<@NonNull AccessLogJpaEntity, @NonNull Long> {

    long count();

    void deleteByCreatedAtLessThanEqual(Instant createdAt);

    Optional<AccessLogJpaEntity> findFirstById(Long id);

}
