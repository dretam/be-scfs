-- MySQL Parameter Permissions Migration
-- Adds PARAMETER_* permissions for parameter management

-- Insert Parameter Permissions
INSERT INTO permissions (name, code, description, created_by, created_at) VALUES
('Parameter Read', 'PARAMETER_READ', 'Permission to read/view parameters', 0, NOW()),
('Parameter Create', 'PARAMETER_CREATE', 'Permission to create new parameters', 0, NOW()),
('Parameter Update', 'PARAMETER_UPDATE', 'Permission to update existing parameters', 0, NOW()),
('Parameter Delete', 'PARAMETER_DELETE', 'Permission to delete parameters', 0, NOW());

-- Link PARAMETER_READ permission to ADMIN role (if exists)
INSERT INTO role_permissions (role_id, permission_id, created_by, created_at)
SELECT r.id, p.id, 0, NOW()
FROM roles r
CROSS JOIN permissions p
WHERE r.code = 'ROLE_ADMIN' AND p.code = 'PARAMETER_READ'
ON DUPLICATE KEY UPDATE created_by = created_by;

-- Link PARAMETER_READ permission to VIEW role (if exists)
INSERT INTO role_permissions (role_id, permission_id, created_by, created_at)
SELECT r.id, p.id, 0, NOW()
FROM roles r
CROSS JOIN permissions p
WHERE r.code = 'ROLE_VIEW' AND p.code = 'PARAMETER_READ'
ON DUPLICATE KEY UPDATE created_by = created_by;
