package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.TokenJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface SpringDataTokenJpaRepository extends JpaRepository<@NonNull TokenJpaEntity, @NonNull Long>, PagingAndSortingRepository<@NonNull TokenJpaEntity, @NonNull Long> {

    long count();

    Optional<TokenJpaEntity> findFirstByHashAndRevokeAtIsNullAndExpiresAtIsGreaterThanEqual(String hash, Instant expiresAtIsGreaterThan);

}
