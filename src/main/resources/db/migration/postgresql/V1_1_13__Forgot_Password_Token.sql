-- ======================
-- TOKENS TABLE
-- ======================
CREATE TABLE IF NOT EXISTS forgot_password_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    token_hash VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    created_at timestamptz NOT NULL DEFAULT now(),
    created_by uuid,
    updated_at timestamptz,
    updated_by uuid,
    deleted_at timestamptz,
    deleted_by uuid,

    CONSTRAINT fk_forgot_password_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
);

-- ======================
-- INDEXES (IMPORTANT 🔥)
-- ======================
CREATE INDEX IF NOT EXISTS idx_forgot_password_token_hash
    ON forgot_password_tokens(token_hash);

CREATE INDEX IF NOT EXISTS idx_forgot_password_user_id
    ON forgot_password_tokens(user_id);

CREATE INDEX IF NOT EXISTS idx_tokens_expires_at
    ON tokens(expires_at);