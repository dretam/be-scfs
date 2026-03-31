-- ======================
-- TOKENS TABLE
-- ======================
CREATE TABLE IF NOT EXISTS tokens (
                                      id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    hash text NOT NULL,
    token_type token_type NOT NULL DEFAULT 'REFRESH',
    expires_at timestamptz DEFAULT now(),
    last_using_at timestamptz,
    revoke_at timestamptz,
    created_at timestamptz NOT NULL DEFAULT now()
    );

-- ======================
-- FOREIGN KEY
-- ======================
ALTER TABLE tokens
    ADD CONSTRAINT fk_tokens_user
        FOREIGN KEY (user_id) REFERENCES users(id)
            ON DELETE CASCADE;

-- ======================
-- INDEXES (IMPORTANT 🔥)
-- ======================
CREATE INDEX IF NOT EXISTS idx_tokens_user_id
    ON tokens(user_id);

CREATE INDEX IF NOT EXISTS idx_tokens_hash
    ON tokens(hash);

CREATE INDEX IF NOT EXISTS idx_tokens_expires_at
    ON tokens(expires_at);