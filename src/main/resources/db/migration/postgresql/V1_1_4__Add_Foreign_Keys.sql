-- ======================
-- USERS
-- ======================
ALTER TABLE users
    ADD CONSTRAINT fk_users_role
        FOREIGN KEY (role_code) REFERENCES roles(code);

ALTER TABLE users
    ADD CONSTRAINT fk_users_company
        FOREIGN KEY (company_id) REFERENCES companies(id);

-- ======================
-- EMAIL
-- ======================
ALTER TABLE email_template_variables
    ADD CONSTRAINT fk_email_template_variables
        FOREIGN KEY (email_template_id) REFERENCES email_templates(id);

ALTER TABLE email_template_specific_emails
    ADD CONSTRAINT fk_email_template_specific_emails_template
        FOREIGN KEY (email_template_id) REFERENCES email_templates(id);

ALTER TABLE email_template_specific_emails
    ADD CONSTRAINT fk_email_template_specific_emails_user
        FOREIGN KEY (user_id) REFERENCES users(id);

-- ======================
-- NOTIFICATIONS
-- ======================
ALTER TABLE notifications
    ADD CONSTRAINT fk_notifications_user
        FOREIGN KEY (read_by) REFERENCES users(id);

-- ======================
-- APPROVAL WORKFLOWS
-- ======================
ALTER TABLE approval_workflows
    ADD CONSTRAINT fk_approval_company
        FOREIGN KEY (company_id) REFERENCES companies(id);

ALTER TABLE approval_workflows
    ADD CONSTRAINT fk_stg1_maker FOREIGN KEY (stg1_maker_user_id) REFERENCES users(id);

ALTER TABLE approval_workflows
    ADD CONSTRAINT fk_stg1_approver FOREIGN KEY (stg1_approver_user_id) REFERENCES users(id);

ALTER TABLE approval_workflows
    ADD CONSTRAINT fk_stg2_maker FOREIGN KEY (stg2_maker_user_id) REFERENCES users(id);

ALTER TABLE approval_workflows
    ADD CONSTRAINT fk_stg2_approver FOREIGN KEY (stg2_approver_user_id) REFERENCES users(id);

ALTER TABLE approval_workflows
    ADD CONSTRAINT fk_stg3_rm FOREIGN KEY (stg3_reviewer_rm_user_id) REFERENCES users(id);

ALTER TABLE approval_workflows
    ADD CONSTRAINT fk_stg3_bm FOREIGN KEY (stg3_reviewer_bm_user_id) REFERENCES users(id);

ALTER TABLE approval_workflows
    ADD CONSTRAINT fk_stg3_checker FOREIGN KEY (stg3_checker_user_id) REFERENCES users(id);

ALTER TABLE approval_workflows
    ADD CONSTRAINT fk_stg3_signer FOREIGN KEY (stg3_signer_user_id) REFERENCES users(id);

-- ======================
-- COMPANIES RELATION
-- ======================
ALTER TABLE companies_communities
    ADD CONSTRAINT fk_companies_communities_company
        FOREIGN KEY (company_id) REFERENCES companies(id);

ALTER TABLE companies_communities
    ADD CONSTRAINT fk_companies_communities_community
        FOREIGN KEY (community_id) REFERENCES communities(id);

ALTER TABLE company_allocation_anchor
    ADD CONSTRAINT fk_company_allocation_company
        FOREIGN KEY (company_id) REFERENCES companies(id);

ALTER TABLE company_allocation_anchor
    ADD CONSTRAINT fk_company_allocation_anchor
        FOREIGN KEY (anchor_id) REFERENCES companies(id);

-- ======================
-- COMPANY ACCOUNTS
-- ======================
ALTER TABLE company_accounts
    ADD CONSTRAINT fk_company_accounts_company
        FOREIGN KEY (company_id) REFERENCES companies(id);

ALTER TABLE company_accounts
    ADD CONSTRAINT fk_company_accounts_community
        FOREIGN KEY (community_id) REFERENCES communities(id);

-- ======================
-- COMMUNITY
-- ======================
ALTER TABLE community_pricing_tiers
    ADD CONSTRAINT fk_community_pricing
        FOREIGN KEY (community_id) REFERENCES communities(id);

-- ======================
-- DOCUMENTS
-- ======================
ALTER TABLE document_template_variables
    ADD CONSTRAINT fk_document_template_variables
        FOREIGN KEY (document_template_id) REFERENCES document_templates(id);

-- ======================
-- REMINDER
-- ======================
ALTER TABLE reminder_users
    ADD CONSTRAINT fk_reminder_users_config
        FOREIGN KEY (reminder_config_id) REFERENCES reminder_configs(id);

ALTER TABLE reminder_users
    ADD CONSTRAINT fk_reminder_users_user
        FOREIGN KEY (user_id) REFERENCES users(id);

-- ======================
-- ACTIVITY LOGS
-- ======================
ALTER TABLE activity_logs
    ADD CONSTRAINT fk_activity_logs_user
        FOREIGN KEY (user_id) REFERENCES users(id);