-- ============================================
-- ENUM TYPES
-- ============================================

CREATE TYPE token_type AS ENUM (
    'REFRESH',
    'OPEN_API',
    'PASSWORD_RESET',
    'EMAIL_VERIFY',
    'PHONE_VERIFY',
    'OTP'
    );

CREATE TYPE http_method AS ENUM (
    'GET',
    'POST',
    'PUT',
    'PATCH',
    'DELETE',
    'HEAD',
    'CONNECT',
    'OPTIONS',
    'TRACE'
    );

-- ============================================
-- TABLES
-- ============================================

CREATE TABLE roles
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL DEFAULT 'ROLE_UNIDENTIFIED',
    icon        VARCHAR(255) NOT NULL DEFAULT 'ph:question',
    description TEXT,
    created_by  BIGINT       NOT NULL DEFAULT 0,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT,
    updated_at  TIMESTAMP,
    deleted_by  BIGINT,
    deleted_at  TIMESTAMP
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(255) UNIQUE NOT NULL,
    email      VARCHAR(255)        NOT NULL,
    password   TEXT                NOT NULL,
    role_id    BIGINT              NOT NULL,
    created_by BIGINT              NOT NULL DEFAULT 0,
    created_at TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT,
    updated_at TIMESTAMP,
    deleted_by BIGINT,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE tokens
(
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT     NOT NULL,
    hash          TEXT       NOT NULL,
    token_type    token_type NOT NULL DEFAULT 'REFRESH',
    expires_at    TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    last_using_at TIMESTAMP,
    revoke_at     TIMESTAMP,
    CONSTRAINT fk_tokens_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE access_logs
(
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT      NOT NULL,
    ip_address       VARCHAR(50) NOT NULL,
    user_agent       TEXT        NOT NULL,
    uri              TEXT        NOT NULL,
    http_method      http_method NOT NULL,
    query_params     TEXT,
    request_body     TEXT,
    status_code      INTEGER     NOT NULL,
    response_time_ms BIGINT      NOT NULL DEFAULT 0,
    error_message    TEXT,
    created_at       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_access_logs_user FOREIGN KEY (user_id) REFERENCES users (id)
);

create table scheduled_tasks
(
    task_name            TEXT                     NOT NULL,
    task_instance        TEXT                     NOT NULL,
    task_data            BYTEA,
    execution_time       TIMESTAMP WITH TIME ZONE NOT NULL,
    picked               BOOLEAN                  NOT NULL,
    picked_by            TEXT,
    last_success         TIMESTAMP WITH TIME ZONE,
    last_failure         TIMESTAMP WITH TIME ZONE,
    consecutive_failures INT,
    last_heartbeat       TIMESTAMP WITH TIME ZONE,
    version              BIGINT                   NOT NULL,
    priority             SMALLINT,
    PRIMARY KEY (task_name, task_instance)
);

CREATE TABLE scheduled_logs
(
    id                   BIGINT                   NOT NULL PRIMARY KEY,
    task_name            TEXT                     NOT NULL,
    task_instance        TEXT                     NOT NULL,
    task_data            BYTEA,
    picked_by            TEXT,
    time_started         TIMESTAMP WITH TIME ZONE NOT NULL,
    time_finished        TIMESTAMP WITH TIME ZONE NOT NULL,
    succeeded            BOOLEAN                  NOT NULL,
    duration_ms          BIGINT                   NOT NULL,
    exception_class      TEXT,
    exception_message    TEXT,
    exception_stacktrace TEXT
);

-- ============================================
-- INDEXES
-- ============================================

CREATE INDEX execution_time_idx ON scheduled_tasks (execution_time, priority desc);
CREATE INDEX last_heartbeat_idx ON scheduled_tasks (last_heartbeat);
CREATE INDEX priority_execution_time_idx on scheduled_tasks (priority desc, execution_time asc);
CREATE INDEX stl_started_idx ON scheduled_logs (time_started);
CREATE INDEX stl_task_name_idx ON scheduled_logs (task_name);
CREATE INDEX stl_exception_class_idx ON scheduled_logs (exception_class);
