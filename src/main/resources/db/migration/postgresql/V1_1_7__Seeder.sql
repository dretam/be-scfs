-- ======================
-- ROLES
-- ======================
INSERT INTO roles (code, name, description, icon, created_by, created_at)
VALUES
    ('ADMIN','Administrator','Full access','fa-user-shield',gen_random_uuid(),now()),
    ('MAKER','Maker','Create data','fa-user-edit',gen_random_uuid(),now()),
    ('APPROVER','Approver','Approve data','fa-user-check',gen_random_uuid(),now())
    ON CONFLICT (code) DO NOTHING;

-- ======================
-- COMPANIES
-- ======================
INSERT INTO companies (cif, name, type_company, created_by, created_at)
VALUES
    ('CIF001','PT Supplier Demo','SUPPLIER',gen_random_uuid(),now()),
    ('CIF002','PT Anchor Demo','ANCHOR',gen_random_uuid(),now())
    ON CONFLICT (cif) DO NOTHING;

-- ======================
-- USERS
-- ======================
INSERT INTO users (
    role_code, company_id, username, password,
    full_name, email, is_active, created_by, created_at
)
VALUES (
           'ADMIN',
           (SELECT id FROM companies WHERE cif='CIF002'),
           'admin',
           '$2a$10$replace_with_real_bcrypt',
           'Super Admin',
           'admin@mail.com',
           true,
           gen_random_uuid(),
           now()
       )
    ON CONFLICT (username) DO NOTHING;

-- ======================
-- COMMUNITIES
-- ======================
INSERT INTO communities (
    name, product_type, recourse, holiday_treatment,
    transaction_fee_type, interest_type, interest_basis,
    is_interest_reserve, is_fund_reserve,
    is_sharing_income, is_extend_financing,
    early_payment_fee_type,
    created_by, created_at
)
VALUES (
           'Default Community',
           'FACTORING',
           'WITH_RECOURSE',
           'NEXT_BUSINESS_DAYS',
           'AMOUNT',
           'SINGLE_RATE',
           'CASH_BASIS',
           false,false,false,false,
           'PERCENT',
           gen_random_uuid(),
           now()
       )
    ON CONFLICT (name) DO NOTHING;

-- ======================
-- COMPANIES_COMMUNITIES
-- ======================
INSERT INTO companies_communities (company_id, community_id)
SELECT c.id, cm.id
FROM companies c, communities cm
    LIMIT 1;

-- ======================
-- COMPANY ACCOUNTS
-- ======================
INSERT INTO company_accounts (
    company_id, community_id,
    loan_account_number, loan_account_bank, loan_account_owner,
    operational_account_number, operational_account_bank, operational_account_owner,
    escrow_account_number, escrow_account_bank, escrow_account_owner,
    created_by, created_at
)
SELECT
    c.id, cm.id,
    '123456','Bank A','Owner A',
    '654321','Bank B','Owner B',
    '999999','Bank C','Owner C',
    gen_random_uuid(), now()
FROM companies c, communities cm
    LIMIT 1;

-- ======================
-- COMPANY ALLOCATION
-- ======================
INSERT INTO company_allocation_anchor (
    company_id, anchor_id, allocation_plafond_amount,
    created_by, created_at
)
SELECT
    s.id,
    a.id,
    100000000,
    gen_random_uuid(),
    now()
FROM companies s, companies a
WHERE s.type_company='SUPPLIER'
  AND a.type_company='ANCHOR'
    LIMIT 1;

-- ======================
-- EMAIL TEMPLATES
-- ======================
INSERT INTO email_templates (
    variant, subject, body, is_active,
    created_by, created_at
)
VALUES
    ('INVOICE_CREATE','Invoice Created','<p>Created</p>',true,gen_random_uuid(),now()),
    ('INVOICE_APPROVE','Invoice Approved','<p>Approved</p>',true,gen_random_uuid(),now())
    ON CONFLICT DO NOTHING;

-- ======================
-- EMAIL VARIABLES
-- ======================
INSERT INTO email_template_variables (
    email_template_id, flag, created_by, created_at
)
SELECT id, 'invoice_number', gen_random_uuid(), now()
FROM email_templates;

-- ======================
-- EMAIL SPECIFIC EMAILS
-- ======================
INSERT INTO email_template_specific_emails (
    email_template_id, email, created_by, created_at
)
SELECT id, 'test@mail.com', gen_random_uuid(), now()
FROM email_templates;

-- ======================
-- DOCUMENT TEMPLATES
-- ======================
INSERT INTO document_templates (
    title, content, footer, created_by, created_at
)
VALUES (
           'Default Doc',
           'Content here',
           'Footer',
           gen_random_uuid(),
           now()
       );

-- ======================
-- DOCUMENT VARIABLES
-- ======================
INSERT INTO document_template_variables (
    document_template_id, flag, created_by, created_at
)
SELECT id, 'customer_name', gen_random_uuid(), now()
FROM document_templates;

-- ======================
-- CALENDAR
-- ======================
INSERT INTO calendars (
    title, start_date, end_date, cycle, created_by, created_at
)
VALUES (
           'Default Calendar',
           CURRENT_DATE,
           CURRENT_DATE + INTERVAL '30 days',
           'MONTHLY',
           gen_random_uuid(),
           now()
       );

-- ======================
-- REMINDER CONFIG
-- ======================
INSERT INTO reminder_configs (
    cycle, variant, created_by, created_at
)
VALUES (
           'DAY_7',
           'DUE_DATE_REMINDER',
           gen_random_uuid(),
           now()
       );

-- ======================
-- REMINDER USERS
-- ======================
INSERT INTO reminder_users (
    reminder_config_id, user_id, sent_at,
    created_by, created_at
)
SELECT rc.id, u.id, CURRENT_DATE, gen_random_uuid(), now()
FROM reminder_configs rc, users u
    LIMIT 1;

-- ======================
-- APPROVAL WORKFLOW
-- ======================
INSERT INTO approval_workflows (
    company_id,
    stg1_is_require_maker, stg1_is_require_approver,
    stg1_is_password_confirm, stg1_is_allow_same_person,
    stg2_is_require_maker, stg2_is_require_approver,
    stg2_is_password_confirm, stg2_is_allow_same_person,
    stg3_require_reviewer_rm, stg3_require_reviewer_bm,
    stg3_require_signer, stg3_password_confirmation,
    stg3_require_checker, stg3_double_checker,
    created_by, created_at
)
SELECT
    id,
    true,true,false,false,
    true,true,false,false,
    true,true,true,false,true,false,
    gen_random_uuid(),
    now()
FROM companies
         LIMIT 1;

-- ======================
-- NOTIFICATIONS
-- ======================
INSERT INTO notifications (
    title, description, created_at
)
VALUES ('Welcome','System initialized',now());

-- ======================
-- RESOURCES
-- ======================
INSERT INTO resources (
    model, path, size, origin_name, name, extension,
    created_by, created_at
)
VALUES (
   'USER',
   '/file/path',
   1234,
   'file.jpg',
   'file',
   'jpg',
   gen_random_uuid(),
   now()
);

-- ======================
-- ACTIVITY LOGS
-- ======================
INSERT INTO activity_logs (
    ip_address, user_agent, uri,
    http_method, status_code, response_time_ms, created_at
)
VALUES (
   '127.0.0.1',
   'Seeder',
   '/init',
   'GET',
   200,
   10,
   now()
);