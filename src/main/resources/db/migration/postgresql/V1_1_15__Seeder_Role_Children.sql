-- SUPPLIER → MAKER
INSERT INTO role_children (code, parent_code, name, description, icon, created_by, created_at)
SELECT 'SUPPLIER_MAKER', r.code, 'Maker', 'Balalala', 'Balalala', gen_random_uuid(), NOW()
FROM roles r
WHERE r.code = 'SUPPLIER'
ON CONFLICT (code) DO NOTHING;

-- SUPPLIER → APPROVER
INSERT INTO role_children (code, parent_code, name, description, icon, created_by, created_at)
SELECT 'SUPPLIER_APPROVER', r.code, 'Approver', 'Balalala', 'Balalala', gen_random_uuid(), NOW()
FROM roles r
WHERE r.code = 'SUPPLIER'
ON CONFLICT (code) DO NOTHING;

-- ANCHOR → MAKER
INSERT INTO role_children (code, parent_code, name, description, icon, created_by, created_at)
SELECT 'ANCHOR_MAKER', r.code, 'Maker', 'Balalala', 'Balalala', gen_random_uuid(), NOW()
FROM roles r
WHERE r.code = 'ANCHOR'
ON CONFLICT (code) DO NOTHING;

-- ANCHOR → APPROVER
INSERT INTO role_children (code, parent_code, name, description, icon, created_by, created_at)
SELECT 'ANCHOR_APPROVER', r.code, 'Approver', 'Balalala', 'Balalala', gen_random_uuid(), NOW()
FROM roles r
WHERE r.code = 'ANCHOR'
ON CONFLICT (code) DO NOTHING;

-- BANK → CHECKER
INSERT INTO role_children (code, parent_code, name, description, icon, created_by, created_at)
SELECT 'BANK_CHECKER', r.code, 'Maker', 'Balalala', 'Balalala', gen_random_uuid(), NOW()
FROM roles r
WHERE r.code = 'BANK'
ON CONFLICT (code) DO NOTHING;

-- BANK → SIGNER
INSERT INTO role_children (code, parent_code, name, description, icon, created_by, created_at)
SELECT 'BANK_SIGNER', r.code, 'Approver', 'Balalala', 'Balalala', gen_random_uuid(), NOW()
FROM roles r
WHERE r.code = 'BANK'
ON CONFLICT (code) DO NOTHING;

-- BANK → ADMIN
INSERT INTO role_children (code, parent_code, name, description, icon, created_by, created_at)
SELECT 'BANK_ADMIN', r.code, 'Admin', 'Balalala', 'Balalala', gen_random_uuid(), NOW()
FROM roles r
WHERE r.code = 'BANK'
ON CONFLICT (code) DO NOTHING;

-- RM → ADMIN
INSERT INTO role_children (code, parent_code, name, description, icon, created_by, created_at)
SELECT 'RM_ADMIN', r.code, 'Admin', 'Balalala', 'Balalala', gen_random_uuid(), NOW()
FROM roles r
WHERE r.code = 'RM'
ON CONFLICT (code) DO NOTHING;

-- RM → REVIEWER
INSERT INTO role_children (code, parent_code, name, description, icon, created_by, created_at)
SELECT 'RM_REVIEWER', r.code, 'Reviewer', 'Balalala', 'Balalala', gen_random_uuid(), NOW()
FROM roles r
WHERE r.code = 'RM'
ON CONFLICT (code) DO NOTHING;