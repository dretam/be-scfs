package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.token.Token;
import bank_mega.corsys.domain.model.token.TokenHash;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.TokenRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.TokenJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.mapper.TokenMapper;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataTokenJpaRepository;
import bank_mega.corsys.infrastructure.util.HashUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataTokenJpaRepository springDataTokenJpaRepository;

    @Override
    public Token save(Token token) {
        return TokenMapper.toDomain(springDataTokenJpaRepository.save(
                TokenMapper.toJpaEntity(token)
        ));
    }

    @Override
    public void delete(Token token) {
        springDataTokenJpaRepository.delete(TokenMapper.toJpaEntity(token));
    }

    @Override
    public long count() {
        return springDataTokenJpaRepository.count();
    }

    @Override
    public Optional<Token> findFirstValidByTokenHash(TokenHash tokenHash) {
        return springDataTokenJpaRepository
                .findFirstByHashAndRevokeAtIsNullAndExpiresAtIsGreaterThanEqual(tokenHash.value(), Instant.now())
                .map(TokenMapper::toDomain);
    }

    @Override
    public String hashToken(String rawToken) {
        return HashUtil.sha256(rawToken);
    }

    @Override
    public void revokeAllTokenRefreshByUser(User user) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<TokenJpaEntity> update = cBuilder.createCriteriaUpdate(TokenJpaEntity.class);
        Root<TokenJpaEntity> root = update.from(TokenJpaEntity.class);

        update
                .set(root.get("revokeAt"), Instant.now())
                .where(
                        cBuilder.and(
                                cBuilder.equal(root.get("user").get("id"), user.getId().value()),
                                cBuilder.isNull(root.get("revokeAt"))
                        )
                );

        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public void lastUsingTokenRefreshByTokenHash(TokenHash hash) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<TokenJpaEntity> update = cBuilder.createCriteriaUpdate(TokenJpaEntity.class);
        Root<TokenJpaEntity> root = update.from(TokenJpaEntity.class);

        update.set(root.get("lastUsingAt"), Instant.now())
                .where(cBuilder.equal(root.get("hash"), hash.value()));

        entityManager.createQuery(update).executeUpdate();
    }

}
