-- MySQL RBAC Enhancement Migration
-- Adds user_permissions table for permission overrides, menu_id to permissions, and code to roles

-- User Permissions Table (Override)
-- Allows overriding role permissions per user with ALLOW/DENY effect
CREATE TABLE user_permissions
(
    user_id       BIGINT       NOT NULL,
    permission_id BIGINT       NOT NULL,
    effect        ENUM ('ALLOW', 'DENY') NOT NULL,
    created_by    BIGINT       NOT NULL DEFAULT 0,
    created_at    DATETIME              DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, permission_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions (id) ON DELETE CASCADE
);

-- Add menu_id to permissions table (link permissions to menu features)
ALTER TABLE permissions
    ADD COLUMN menu_id BIGINT NULL AFTER description,
    ADD CONSTRAINT fk_permissions_menu FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE SET NULL;

-- Add code to roles table (for consistent role identification)
ALTER TABLE roles
    ADD COLUMN code VARCHAR(100) NULL AFTER name;

-- Create index for user_permissions
CREATE INDEX idx_user_permissions_user_id ON user_permissions (user_id);
CREATE INDEX idx_user_permissions_effect ON user_permissions (effect);

-- Create index for permissions menu_id
CREATE INDEX idx_permissions_menu_id ON permissions (menu_id);

-- Create index for roles code
CREATE INDEX idx_roles_code ON roles (code);
