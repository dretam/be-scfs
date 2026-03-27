-- ======================
-- ROLES
-- ======================
CREATE TABLE roles (
                       code varchar(255) PRIMARY KEY,
                       name varchar(255),
                       description text,
                       icon varchar(255) NOT NULL,
                       created_by uuid NOT NULL,
                       created_at timestamptz NOT NULL,
                       updated_by uuid,
                       updated_at timestamptz,
                       deleted_by uuid,
                       deleted_at timestamptz
);

-- ======================
-- COMPANIES
-- ======================
CREATE TABLE companies (
                           id uuid PRIMARY KEY,
                           cif varchar(255) UNIQUE NOT NULL,
                           name varchar(255) NOT NULL,
                           type_company companies_type_company NOT NULL,
                           rm_user_id uuid,
                           discount_rate int NOT NULL DEFAULT 0,
                           max_financing int NOT NULL DEFAULT 0,
                           created_by uuid NOT NULL,
                           created_at timestamptz NOT NULL,
                           updated_by uuid,
                           updated_at timestamptz,
                           deleted_by uuid,
                           deleted_at timestamptz
);

-- ======================
-- USERS
-- ======================
CREATE TABLE users (
                       id uuid PRIMARY KEY,
                       role_code varchar(255) NOT NULL,
                       company_id uuid NOT NULL,
                       username varchar(255) UNIQUE NOT NULL,
                       password varchar(255) NOT NULL,
                       full_name varchar(255) NOT NULL,
                       email varchar(255) NOT NULL,
                       is_active boolean NOT NULL,
                       photo_path text,
                       created_by uuid NOT NULL,
                       created_at timestamptz NOT NULL,
                       updated_by uuid,
                       updated_at timestamptz,
                       deleted_by uuid,
                       deleted_at timestamptz
);

-- ======================
-- EMAIL TEMPLATES
-- ======================
CREATE TABLE email_templates (
                                 id uuid PRIMARY KEY,
                                 variant email_templates_variant NOT NULL,
                                 subject text NOT NULL,
                                 body text,
                                 is_active boolean NOT NULL DEFAULT false,
                                 is_send_to_anchor boolean NOT NULL DEFAULT false,
                                 is_send_to_supplier boolean NOT NULL DEFAULT false,
                                 is_send_to_admin_bank boolean NOT NULL DEFAULT false,
                                 cc_is_send_to_rm boolean NOT NULL DEFAULT false,
                                 cc_is_send_to_checker boolean NOT NULL DEFAULT false,
                                 cc_is_send_to_signer boolean NOT NULL DEFAULT false,
                                 cc_is_send_to_specified_mail boolean NOT NULL DEFAULT false,
                                 created_by uuid NOT NULL,
                                 created_at timestamptz NOT NULL,
                                 updated_by uuid,
                                 updated_at timestamptz,
                                 deleted_by uuid,
                                 deleted_at timestamptz
);

CREATE TABLE email_template_variables (
                                          id uuid PRIMARY KEY,
                                          email_template_id uuid NOT NULL,
                                          flag varchar(255) NOT NULL,
                                          created_by uuid NOT NULL,
                                          created_at timestamptz NOT NULL,
                                          updated_by uuid,
                                          updated_at timestamptz,
                                          deleted_by uuid,
                                          deleted_at timestamptz
);

CREATE TABLE email_template_specific_emails (
                                                id uuid PRIMARY KEY,
                                                email_template_id uuid NOT NULL,
                                                user_id uuid,
                                                email varchar(255),
                                                created_by uuid NOT NULL,
                                                created_at timestamptz NOT NULL,
                                                updated_by uuid,
                                                updated_at timestamptz,
                                                deleted_by uuid,
                                                deleted_at timestamptz
);

-- ======================
-- COMMUNITIES
-- ======================
CREATE TABLE communities (
                             id uuid PRIMARY KEY,
                             name varchar(255) UNIQUE NOT NULL,
                             description text,
                             product_type communities_product_type NOT NULL,
                             recourse communities_recourse NOT NULL,
                             holiday_treatment communities_holiday_treatment NOT NULL,
                             transaction_fee_type communities_transaction_fee_type NOT NULL,
                             interest_type communities_interest_type NOT NULL,
                             interest_basis communities_interest_basis NOT NULL,
                             is_active boolean NOT NULL DEFAULT true,
                             is_interest_reserve boolean NOT NULL,
                             is_fund_reserve boolean NOT NULL,
                             is_sharing_income boolean NOT NULL,
                             is_extend_financing boolean NOT NULL,
                             transaction_fee numeric NOT NULL DEFAULT 0,
                             min_financing int NOT NULL DEFAULT 0,
                             max_financing int NOT NULL DEFAULT 0,
                             min_tenor int NOT NULL DEFAULT 0,
                             max_tenor int NOT NULL DEFAULT 0,
                             grace_period int NOT NULL DEFAULT 0,
                             penalty_rate int NOT NULL DEFAULT 0,
                             early_payment_fee_type communities_early_payment_fee_type NOT NULL,
                             early_payment_fee_amount bigint NOT NULL DEFAULT 0,
                             created_by uuid NOT NULL,
                             created_at timestamptz NOT NULL,
                             updated_by uuid,
                             updated_at timestamptz,
                             deleted_by uuid,
                             deleted_at timestamptz
);

CREATE TABLE community_pricing_tiers (
                                         id uuid PRIMARY KEY,
                                         community_id uuid NOT NULL,
                                         logic communities_pricing_tier_logics NOT NULL,
                                         nominal numeric NOT NULL,
                                         quantity int NOT NULL DEFAULT 0,
                                         created_by uuid NOT NULL,
                                         created_at timestamptz NOT NULL,
                                         updated_by uuid,
                                         updated_at timestamptz,
                                         deleted_by uuid,
                                         deleted_at timestamptz
);

-- ======================
-- RELATION TABLES
-- ======================
CREATE TABLE companies_communities (
                                       id uuid PRIMARY KEY,
                                       company_id uuid NOT NULL,
                                       community_id uuid NOT NULL
);

CREATE TABLE company_accounts (
                                  id uuid PRIMARY KEY,
                                  company_id uuid NOT NULL,
                                  community_id uuid NOT NULL,
                                  loan_account_number varchar(255) NOT NULL,
                                  loan_account_bank varchar(255) NOT NULL,
                                  loan_account_owner varchar(255) NOT NULL,
                                  loan_account_balance numeric NOT NULL DEFAULT 0,
                                  operational_account_number varchar(255) NOT NULL,
                                  operational_account_bank varchar(255) NOT NULL,
                                  operational_account_owner varchar(255) NOT NULL,
                                  escrow_account_number varchar(255) NOT NULL,
                                  escrow_account_bank varchar(255) NOT NULL,
                                  escrow_account_owner varchar(255) NOT NULL,
                                  created_by uuid NOT NULL,
                                  created_at timestamptz NOT NULL,
                                  updated_by uuid,
                                  updated_at timestamptz,
                                  deleted_by uuid,
                                  deleted_at timestamptz
);

CREATE TABLE company_allocation_anchor (
                                           id uuid PRIMARY KEY,
                                           company_id uuid NOT NULL,
                                           anchor_id uuid NOT NULL,
                                           allocation_plafond_amount numeric NOT NULL DEFAULT 0,
                                           created_by uuid NOT NULL,
                                           created_at timestamptz NOT NULL,
                                           updated_by uuid,
                                           updated_at timestamptz,
                                           deleted_by uuid,
                                           deleted_at timestamptz
);

-- ======================
-- DOCUMENTS
-- ======================
CREATE TABLE document_templates (
                                    id uuid PRIMARY KEY,
                                    title text NOT NULL,
                                    content text,
                                    footer text,
                                    created_by uuid NOT NULL,
                                    created_at timestamptz NOT NULL,
                                    updated_by uuid,
                                    updated_at timestamptz,
                                    deleted_by uuid,
                                    deleted_at timestamptz
);

CREATE TABLE document_template_variables (
                                             id uuid PRIMARY KEY,
                                             document_template_id uuid NOT NULL,
                                             flag varchar(255) NOT NULL,
                                             created_by uuid NOT NULL,
                                             created_at timestamptz NOT NULL,
                                             updated_by uuid,
                                             updated_at timestamptz,
                                             deleted_by uuid,
                                             deleted_at timestamptz
);

-- ======================
-- CALENDAR & REMINDER
-- ======================
CREATE TABLE calendars (
                           id uuid PRIMARY KEY,
                           title varchar(255) NOT NULL,
                           description text,
                           start_date date NOT NULL,
                           end_date date NOT NULL,
                           cycle calendars_cycle NOT NULL,
                           created_by uuid NOT NULL,
                           created_at timestamptz NOT NULL,
                           updated_by uuid,
                           updated_at timestamptz,
                           deleted_by uuid,
                           deleted_at timestamptz
);

CREATE TABLE reminder_configs (
                                  id uuid PRIMARY KEY,
                                  cycle reminder_configs_cycle NOT NULL,
                                  variant reminder_configs_variant NOT NULL,
                                  created_by uuid NOT NULL,
                                  created_at timestamptz NOT NULL,
                                  updated_by uuid,
                                  updated_at timestamptz,
                                  deleted_by uuid,
                                  deleted_at timestamptz
);

CREATE TABLE reminder_users (
                                id uuid PRIMARY KEY,
                                reminder_config_id uuid NOT NULL,
                                user_id uuid NOT NULL,
                                sent_at date NOT NULL,
                                is_sent boolean NOT NULL DEFAULT false,
                                created_by uuid NOT NULL,
                                created_at timestamptz NOT NULL,
                                updated_by uuid,
                                updated_at timestamptz,
                                deleted_by uuid,
                                deleted_at timestamptz
);

-- ======================
-- SYSTEM TABLES
-- ======================
CREATE TABLE notifications (
                               id uuid PRIMARY KEY,
                               title varchar(255) NOT NULL,
                               description text,
                               data jsonb,
                               is_redirect boolean,
                               path text,
                               created_at timestamptz,
                               read_at timestamptz,
                               read_by uuid
);

CREATE TABLE resources (
                           id uuid PRIMARY KEY,
                           model varchar(255) NOT NULL,
                           path text NOT NULL,
                           size bigint NOT NULL,
                           origin_name text NOT NULL,
                           name text NOT NULL,
                           extension varchar(255) NOT NULL,
                           created_by uuid NOT NULL,
                           created_at timestamptz NOT NULL
);

CREATE TABLE activity_logs (
                               id uuid PRIMARY KEY,
                               user_id uuid,
                               ip_address varchar(255) NOT NULL,
                               user_agent varchar(255) NOT NULL,
                               uri varchar(255) NOT NULL,
                               http_method activity_logs_http_method NOT NULL,
                               query_param varchar(255),
                               request_body varchar(255),
                               status_code int NOT NULL,
                               response_time_ms bigint NOT NULL,
                               error_message text,
                               created_at timestamptz
);

-- ======================
-- APPROVAL WORKFLOW
-- ======================
CREATE TABLE approval_workflows (
                                    id uuid PRIMARY KEY,
                                    company_id uuid,
                                    is_active boolean NOT NULL DEFAULT true,
                                    stg1_is_require_maker boolean NOT NULL,
                                    stg1_is_require_approver boolean NOT NULL,
                                    stg1_is_password_confirm boolean NOT NULL,
                                    stg1_is_allow_same_person boolean NOT NULL,
                                    stg1_maker_user_id uuid,
                                    stg1_approver_user_id uuid,
                                    stg2_is_require_maker boolean NOT NULL,
                                    stg2_is_require_approver boolean NOT NULL,
                                    stg2_is_password_confirm boolean NOT NULL,
                                    stg2_is_allow_same_person boolean NOT NULL,
                                    stg2_maker_user_id uuid,
                                    stg2_approver_user_id uuid,
                                    stg3_require_reviewer_rm boolean NOT NULL,
                                    stg3_require_reviewer_bm boolean NOT NULL,
                                    stg3_require_signer boolean NOT NULL,
                                    stg3_password_confirmation boolean NOT NULL,
                                    stg3_require_checker boolean NOT NULL,
                                    stg3_double_checker boolean NOT NULL,
                                    stg3_reviewer_rm_user_id uuid,
                                    stg3_reviewer_bm_user_id uuid,
                                    stg3_checker_user_id uuid,
                                    stg3_signer_user_id uuid,
                                    glb_is_enable_audit_trail boolean NOT NULL DEFAULT true,
                                    glb_is_auto_progress boolean NOT NULL DEFAULT true,
                                    glb_is_enable_rollback boolean NOT NULL DEFAULT true,
                                    created_by uuid NOT NULL,
                                    created_at timestamptz NOT NULL,
                                    updated_by uuid,
                                    updated_at timestamptz,
                                    deleted_by uuid,
                                    deleted_at timestamptz
);