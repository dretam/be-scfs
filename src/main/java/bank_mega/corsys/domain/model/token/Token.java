package bank_mega.corsys.domain.model.token;

import bank_mega.corsys.domain.model.user.User;

import java.time.Instant;

public class Token {

    private final TokenId id;
    private final User user;
    private final TokenHash hash;
    private final TokenType type;
    private final Instant expiresAt;
    private Instant lastUsingAt;
    private Instant revokedAt;

    public Token(
            TokenId id,
            User user,
            TokenHash hash,
            TokenType type,
            Instant expiresAt,
            Instant lastUsingAt,
            Instant revokedAt
    ) {
        this.id = id;
        this.user = user;
        this.hash = hash;
        this.type = type;
        this.expiresAt = expiresAt;
        this.lastUsingAt = lastUsingAt;
        this.revokedAt = revokedAt;
    }

    public TokenId getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public TokenHash getHash() {
        return this.hash;
    }

    public TokenType getType() {
        return this.type;
    }

    public Instant getExpiresAt() {
        return this.expiresAt;
    }

    public Instant getLastUsingAt() {
        return this.lastUsingAt;
    }

    public Instant getRevokedAt() {
        return this.revokedAt;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public boolean isRevoked() {
        return revokedAt != null;
    }

}
