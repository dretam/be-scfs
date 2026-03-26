CREATE TYPE user_type AS ENUM ('INTERNAL', 'EXTERNAL');

ALTER TABLE users ADD COLUMN type user_type NOT NULL DEFAULT 'INTERNAL';

CREATE INDEX idx_users_type ON users(type);

COMMENT ON COLUMN users.type IS 'User type: INTERNAL (employee) or EXTERNAL (client/partner)';