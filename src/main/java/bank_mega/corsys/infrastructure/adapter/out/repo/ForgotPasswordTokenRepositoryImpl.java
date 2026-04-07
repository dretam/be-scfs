package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.forgotpasstoken.ForgotToken;
import bank_mega.corsys.domain.model.forgotpasstoken.ForgotTokenHash;
import bank_mega.corsys.domain.model.token.Token;
import bank_mega.corsys.domain.model.token.TokenHash;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ForgotTokenRepository;
import bank_mega.corsys.domain.repository.TokenRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.TokenJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataForgotPasswordTokenJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataTokenJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.ForgotPasswordTokenMapper;
import bank_mega.corsys.infrastructure.adapter.out.mapper.TokenMapper;
import bank_mega.corsys.infrastructure.util.HashUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Repository
@RequiredArgsConstructor
public class ForgotPasswordTokenRepositoryImpl implements ForgotTokenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataForgotPasswordTokenJpaRepository springDataForgotPasswordTokenJpaRepository;

    @Override
    public ForgotToken save(ForgotToken token) {
        return ForgotPasswordTokenMapper.toDomain(springDataForgotPasswordTokenJpaRepository.save(
                ForgotPasswordTokenMapper.toJpaEntity(token)
        ));
    }

    @Override
    public void delete(ForgotToken token) {
        springDataForgotPasswordTokenJpaRepository.delete(ForgotPasswordTokenMapper.toJpaEntity(token));
    }

    @Override
    public long count() {
        return springDataForgotPasswordTokenJpaRepository.count();
    }

    @Override
    public Optional<ForgotToken> findFirstValidByForgotTokenHash(ForgotTokenHash tokenHash) {
        return springDataForgotPasswordTokenJpaRepository
                .findFirstByTokenHashAndUsedIsFalseAndExpiresAtIsGreaterThanEqual(tokenHash.value(), Instant.now())
                .map(ForgotPasswordTokenMapper::toDomain);
    }

    @Override
    public String hashToken(String rawToken) {
        return HashUtil.sha256(rawToken);
    }

}
