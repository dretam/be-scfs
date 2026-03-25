-- PostgreSQL Parameter Permissions Migration
-- Adds PARAMETER_* permissions for parameter management

-- Insert Parameter Permissions
INSERT INTO permissions (name, code, description, created_by, created_at) VALUES
('Parameter Read', 'PARAMETER_READ', 'Permission to read/view parameters', 0, CURRENT_TIMESTAMP),
('Parameter Create', 'PARAMETER_CREATE', 'Permission to create new parameters', 0, CURRENT_TIMESTAMP),
('Parameter Update', 'PARAMETER_UPDATE', 'Permission to update existing parameters', 0, CURRENT_TIMESTAMP),
('Parameter Delete', 'PARAMETER_DELETE', 'Permission to delete parameters', 0, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- Link PARAMETER_READ permission to ADMIN role (if exists)
INSERT INTO role_permissions (role_id, permission_id, created_by, created_at)
SELECT r.id, p.id, 0, CURRENT_TIMESTAMP
FROM roles r
CROSS JOIN permissions p
WHERE r.code = 'ROLE_ADMIN' AND p.code = 'PARAMETER_READ'
ON CONFLICT (role_id, permission_id) DO NOTHING;

-- Link PARAMETER_READ permission to VIEW role (if exists)
INSERT INTO role_permissions (role_id, permission_id, created_by, created_at)
SELECT r.id, p.id, 0, CURRENT_TIMESTAMP
FROM roles r
CROSS JOIN permissions p
WHERE r.code = 'ROLE_VIEW' AND p.code = 'PARAMETER_READ'
ON CONFLICT (role_id, permission_id) DO NOTHING;
