-- ======================
-- EMAIL
-- ======================
COMMENT ON COLUMN email_template_variables.flag IS 'Snake Case format';

COMMENT ON COLUMN email_templates.body IS 'HTML Format';

-- ======================
-- COMPANIES
-- ======================
COMMENT ON COLUMN companies.discount_rate IS 'Percent';

COMMENT ON COLUMN companies.max_financing IS 'Percent';

-- ======================
-- APPROVAL WORKFLOW
-- ======================
COMMENT ON COLUMN approval_workflows.company_id IS 'Global Is Null';

-- ======================
-- EMAIL SPECIFIC
-- ======================
COMMENT ON COLUMN email_template_specific_emails.user_id IS 'When choosing from user';

-- ======================
-- COMPANY ALLOCATION
-- ======================
COMMENT ON COLUMN company_allocation_anchor.anchor_id IS 'Company Anchor Only';

-- ======================
-- DOCUMENT
-- ======================
COMMENT ON COLUMN document_template_variables.flag IS 'Snake Case format';

-- ======================
-- COMPANY ACCOUNT
-- ======================
COMMENT ON COLUMN company_accounts.loan_account_balance IS 'Total Plafond Limit';

-- ======================
-- COMMUNITIES
-- ======================
COMMENT ON COLUMN communities.min_financing IS 'Percent';

COMMENT ON COLUMN communities.max_financing IS 'Percent';

COMMENT ON COLUMN communities.min_tenor IS 'Days';

COMMENT ON COLUMN communities.max_tenor IS 'Days';

COMMENT ON COLUMN communities.grace_period IS 'Days';

COMMENT ON COLUMN communities.penalty_rate IS 'Percent';