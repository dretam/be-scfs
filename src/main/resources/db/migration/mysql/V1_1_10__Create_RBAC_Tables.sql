-- MySQL RBAC Tables Migration

-- Permissions Table
CREATE TABLE permissions
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    code        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT         NULL,
    created_by  BIGINT       NOT NULL DEFAULT 0,
    created_at  DATETIME              DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT       NULL,
    updated_at  DATETIME     NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT       NULL,
    deleted_at  DATETIME     NULL
);

-- Menus Table
CREATE TABLE menus
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    code        VARCHAR(100) NOT NULL UNIQUE,
    path        VARCHAR(500) NULL,
    icon        VARCHAR(100) NULL,
    parent_id   BIGINT       NULL,
    sort_order  INT          NOT NULL DEFAULT 0,
    created_by  BIGINT       NOT NULL DEFAULT 0,
    created_at  DATETIME              DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT       NULL,
    updated_at  DATETIME     NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT       NULL,
    deleted_at  DATETIME     NULL,
    FOREIGN KEY (parent_id) REFERENCES menus (id)
);

-- Role Permissions Table (Many-to-Many)
CREATE TABLE role_permissions
(
    role_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    created_by    BIGINT NOT NULL DEFAULT 0,
    created_at    DATETIME        DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions (id) ON DELETE CASCADE
);

-- Role Menus Table (Many-to-Many)
CREATE TABLE role_menus
(
    role_id  BIGINT NOT NULL,
    menu_id  BIGINT NOT NULL,
    created_by BIGINT NOT NULL DEFAULT 0,
    created_at DATETIME        DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (role_id, menu_id),
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_permissions_code ON permissions (code);
CREATE INDEX idx_menus_code ON menus (code);
CREATE INDEX idx_menus_parent_id ON menus (parent_id);
CREATE INDEX idx_menus_sort_order ON menus (sort_order);
