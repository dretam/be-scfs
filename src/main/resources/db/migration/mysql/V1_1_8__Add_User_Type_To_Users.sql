-- Add type column to users table
ALTER TABLE users 
ADD COLUMN type ENUM('INTERNAL', 'EXTERNAL') NOT NULL DEFAULT 'INTERNAL' AFTER role_id;

-- Add index for type filtering
CREATE INDEX idx_users_type ON users(type);

-- Add comment for documentation
ALTER TABLE users MODIFY COLUMN type ENUM('INTERNAL', 'EXTERNAL') NOT NULL DEFAULT 'INTERNAL' COMMENT 'User type: INTERNAL (employee) or EXTERNAL (client/partner)';
