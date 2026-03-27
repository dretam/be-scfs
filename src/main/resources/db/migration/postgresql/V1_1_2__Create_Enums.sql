DO $$ BEGIN
CREATE TYPE activity_logs_http_method AS ENUM
    ('GET','POST','PUT','DELETE','PATCH','HEAD','OPTIONS','TRACE','CONNECT');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE calendars_cycle AS ENUM ('YEARLY','MONTHLY','DAILY');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE communities_early_payment_fee_type AS ENUM ('PERCENT','AMOUNT');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE communities_holiday_treatment AS ENUM
    ('NEXT_BUSINESS_DAYS','PREVIOUS_BUSINESS_DAYS');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE communities_interest_basis AS ENUM
    ('CASH_BASIS','ACCRUAL','AMORTIZATION');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE communities_interest_type AS ENUM
    ('TIERING','SINGLE_RATE');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE communities_pricing_tier_logics AS ENUM
    ('LT','LTE','GT','GTE','EQUAL');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE communities_product_type AS ENUM
    ('FACTORING','FINANCING');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE communities_recourse AS ENUM
    ('WITH_RECOURSE','WITHOUT_RECOURSE');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE communities_transaction_fee_type AS ENUM
    ('AMOUNT','PERCENTAGE');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE companies_type_company AS ENUM
    ('SUPPLIER','ANCHOR');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE email_templates_variant AS ENUM
    ('INVOICE_CREATE','INVOICE_APPROVE','INVOICE_REJECT',
     'INVOICE_MODIFICATION','FUND_DISBURSED',
     'DUE_DATE_REMINDER','DAILY_SUMMARY');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE reminder_configs_cycle AS ENUM
    ('DAY_7','DAY_0','MONTHLY');
EXCEPTION WHEN duplicate_object THEN null; END $$;

DO $$ BEGIN
CREATE TYPE reminder_configs_variant AS ENUM
    ('DUE_DATE_REMINDER');
EXCEPTION WHEN duplicate_object THEN null; END $$;