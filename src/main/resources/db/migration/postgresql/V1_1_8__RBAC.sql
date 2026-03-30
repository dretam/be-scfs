-- ======================
-- MENUS
-- ======================
CREATE TABLE IF NOT EXISTS menus (
                                     id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(255) NOT NULL,
    code varchar(100) NOT NULL UNIQUE,
    path varchar(500),
    icon varchar(100),
    parent_id uuid,
    sort_order integer NOT NULL DEFAULT 0,
    created_by uuid NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_by uuid,
    updated_at timestamptz,
    deleted_by uuid,
    deleted_at timestamptz
    );

ALTER TABLE menus
    ADD CONSTRAINT fk_menus_parent
        FOREIGN KEY (parent_id) REFERENCES menus(id);

-- ======================
-- PERMISSIONS
-- ======================
CREATE TABLE IF NOT EXISTS permissions (
                                           id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(255) NOT NULL,
    code varchar(100) NOT NULL UNIQUE,
    description text,
    menu_id uuid,
    created_by uuid NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_by uuid,
    updated_at timestamptz,
    deleted_by uuid,
    deleted_at timestamptz
    );

ALTER TABLE permissions
    ADD CONSTRAINT fk_permissions_menu
        FOREIGN KEY (menu_id) REFERENCES menus(id)
            ON DELETE SET NULL;

-- ======================
-- ROLE MENUS
-- ======================
CREATE TABLE IF NOT EXISTS role_menus (
                                          role_code varchar(255) NOT NULL,
    menu_id uuid NOT NULL,
    created_by uuid NOT NULL,
    created_at timestamptz DEFAULT now(),
    PRIMARY KEY (role_code, menu_id)
    );

ALTER TABLE role_menus
    ADD CONSTRAINT fk_role_menus_role
        FOREIGN KEY (role_code) REFERENCES roles(code)
            ON DELETE CASCADE;

ALTER TABLE role_menus
    ADD CONSTRAINT fk_role_menus_menu
        FOREIGN KEY (menu_id) REFERENCES menus(id)
            ON DELETE CASCADE;

-- ======================
-- ROLE PERMISSIONS
-- ======================
CREATE TABLE IF NOT EXISTS role_permissions (
                                                role_code varchar(255) NOT NULL,
    permission_id uuid NOT NULL,
    created_by uuid NOT NULL,
    created_at timestamptz DEFAULT now(),
    PRIMARY KEY (role_code, permission_id)
    );

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_role_permissions_role
        FOREIGN KEY (role_code) REFERENCES roles(code)
            ON DELETE CASCADE;

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_role_permissions_permission
        FOREIGN KEY (permission_id) REFERENCES permissions(id)
            ON DELETE CASCADE;

-- ======================
-- USER PERMISSIONS
-- ======================
CREATE TABLE IF NOT EXISTS user_permissions (
                                                user_id uuid NOT NULL,
                                                permission_id uuid NOT NULL,
                                                effect varchar(10) NOT NULL,
    created_by uuid NOT NULL,
    created_at timestamptz DEFAULT now(),
    PRIMARY KEY (user_id, permission_id),
    CONSTRAINT chk_user_permissions_effect
    CHECK (effect IN ('ALLOW','DENY'))
    );

ALTER TABLE user_permissions
    ADD CONSTRAINT fk_user_permissions_user
        FOREIGN KEY (user_id) REFERENCES users(id)
            ON DELETE CASCADE;

ALTER TABLE user_permissions
    ADD CONSTRAINT fk_user_permissions_permission
        FOREIGN KEY (permission_id) REFERENCES permissions(id)
            ON DELETE CASCADE;

-- ======================
-- INDEXES
-- ======================
CREATE INDEX IF NOT EXISTS idx_menus_parent_id ON menus(parent_id);
CREATE INDEX IF NOT EXISTS idx_menus_sort_order ON menus(sort_order);

CREATE INDEX IF NOT EXISTS idx_permissions_menu_id ON permissions(menu_id);

CREATE INDEX IF NOT EXISTS idx_user_permissions_user_id ON user_permissions(user_id);
CREATE INDEX IF NOT EXISTS idx_user_permissions_effect ON user_permissions(effect);