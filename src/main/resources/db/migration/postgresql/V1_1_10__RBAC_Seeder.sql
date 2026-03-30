-- ======================
-- MENUS (ROOT)
-- ======================
INSERT INTO menus (name, code, path, icon, sort_order, created_by)
VALUES
    ('Dashboard','DASHBOARD','/dashboard','fa-dashboard',1,gen_random_uuid()),
    ('User Management','USER_MGMT',NULL,'fa-users',2,gen_random_uuid()),
    ('Master Data','MASTER_DATA',NULL,'fa-database',3,gen_random_uuid())
    ON CONFLICT (code) DO NOTHING;

-- ======================
-- MENUS (CHILDREN)
-- ======================
INSERT INTO menus (name, code, path, icon, parent_id, sort_order, created_by)
VALUES
    (
        'Users',
        'MENU_USERS',
        '/users',
        'fa-user',
        (SELECT id FROM menus WHERE code='USER_MGMT'),
        1,
        gen_random_uuid()
    ),
    (
        'Roles',
        'MENU_ROLES',
        '/roles',
        'fa-id-badge',
        (SELECT id FROM menus WHERE code='USER_MGMT'),
        2,
        gen_random_uuid()
    ),
    (
        'Permissions',
        'MENU_PERMISSIONS',
        '/permissions',
        'fa-key',
        (SELECT id FROM menus WHERE code='USER_MGMT'),
        3,
        gen_random_uuid()
    )
    ON CONFLICT (code) DO NOTHING;

-- ======================
-- PERMISSIONS
-- ======================
INSERT INTO permissions (name, code, menu_id, created_by)
VALUES
    (
        'View Users',
        'USER_VIEW',
        (SELECT id FROM menus WHERE code='MENU_USERS'),
        gen_random_uuid()
    ),
    (
        'Create User',
        'USER_CREATE',
        (SELECT id FROM menus WHERE code='MENU_USERS'),
        gen_random_uuid()
    ),
    (
        'Update User',
        'USER_UPDATE',
        (SELECT id FROM menus WHERE code='MENU_USERS'),
        gen_random_uuid()
    ),
    (
        'Delete User',
        'USER_DELETE',
        (SELECT id FROM menus WHERE code='MENU_USERS'),
        gen_random_uuid()
    )
    ON CONFLICT (code) DO NOTHING;

-- ======================
-- ROLE MENUS (ADMIN = ALL)
-- ======================
INSERT INTO role_menus (role_code, menu_id, created_by)
SELECT
    'ADMIN',
    m.id,
    gen_random_uuid()
FROM menus m
    ON CONFLICT DO NOTHING;

-- ======================
-- ROLE PERMISSIONS (ADMIN = ALL)
-- ======================
INSERT INTO role_permissions (role_code, permission_id, created_by)
SELECT
    'ADMIN',
    p.id,
    gen_random_uuid()
FROM permissions p
    ON CONFLICT DO NOTHING;

-- ======================
-- MAKER ROLE (LIMITED)
-- ======================
INSERT INTO role_permissions (role_code, permission_id, created_by)
SELECT
    'MAKER',
    p.id,
    gen_random_uuid()
FROM permissions p
WHERE p.code IN ('USER_VIEW','USER_CREATE')
    ON CONFLICT DO NOTHING;

-- ======================
-- APPROVER ROLE
-- ======================
INSERT INTO role_permissions (role_code, permission_id, created_by)
SELECT
    'APPROVER',
    p.id,
    gen_random_uuid()
FROM permissions p
WHERE p.code IN ('USER_VIEW','USER_UPDATE')
    ON CONFLICT DO NOTHING;

-- ======================
-- USER PERMISSION OVERRIDE (EXAMPLE)
-- ======================
INSERT INTO user_permissions (user_id, permission_id, effect, created_by)
SELECT
    u.id,
    p.id,
    'DENY',
    gen_random_uuid()
FROM users u, permissions p
WHERE u.username = 'admin'
  AND p.code = 'USER_DELETE'
    LIMIT 1
ON CONFLICT DO NOTHING;