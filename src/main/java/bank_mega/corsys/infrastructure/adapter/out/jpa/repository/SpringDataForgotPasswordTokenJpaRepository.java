package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ForgotPasswordTokenJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.TokenJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataForgotPasswordTokenJpaRepository extends JpaRepository<@NonNull ForgotPasswordTokenJpaEntity,
        @NonNull UUID>, PagingAndSortingRepository<@NonNull ForgotPasswordTokenJpaEntity, @NonNull UUID> {

    long count();

    Optional<ForgotPasswordTokenJpaEntity> findFirstByTokenHashAndUsedIsFalseAndExpiresAtIsGreaterThanEqual(String tokenHash,
                                                                                                        Instant expiresAtGreaterThan);
}
