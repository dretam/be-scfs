CREATE TABLE roles
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL DEFAULT 'ROLE_UNIDENTIFIED',
    icon        VARCHAR(255) NOT NULL DEFAULT 'ph:question',
    description TEXT         NULL,
    created_by  BIGINT       NOT NULL DEFAULT 0,
    created_at  DATETIME              DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT       NULL,
    updated_at  DATETIME     NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT       NULL,
    deleted_at  DATETIME     NULL
);

CREATE TABLE users
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(255) UNIQUE NOT NULL,
    email      VARCHAR(255)        NOT NULL,
    password   TEXT                NOT NULL,
    role_id    BIGINT              NOT NULL,
    created_by BIGINT              NOT NULL DEFAULT 0,
    created_at DATETIME                     DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT              NULL,
    updated_at DATETIME            NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by BIGINT              NULL,
    deleted_at DATETIME            NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE tokens
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id       BIGINT   NOT NULL,
    hash          TEXT     NOT NULL,
    token_type          ENUM ('REFRESH', 'OPEN_API', 'PASSWORD_RESET', 'EMAIL_VERIFY', 'PHONE_VERIFY', 'OTP'),
    expires_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_using_at DATETIME NULL,
    revoke_at     DATETIME NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE access_logs
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id          BIGINT                                                                                NOT NULL,
    ip_address       VARCHAR(50)                                                                           NOT NULL,
    user_agent       TEXT                                                                                  NOT NULL,
    uri              TEXT                                                                                  NOT NULL,
    http_method      ENUM ('GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'HEAD', 'CONNECT', 'OPTIONS', 'TRACE') NOT NULL,
    query_params     TEXT                                                                                  NULL,
    request_body     TEXT                                                                                  NULL,
    status_code      INTEGER                                                                               NOT NULL,
    response_time_ms BIGINT                                                                                NOT NULL DEFAULT 0,
    error_message    TEXT                                                                                  NULL,
    created_at       DATETIME                                                                                       DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

create table scheduled_tasks
(
    task_name            varchar(100) not null,
    task_instance        varchar(100) not null,
    task_data            blob,
    execution_time       timestamp(6) not null,
    picked               BOOLEAN      not null,
    picked_by            varchar(50),
    last_success         timestamp(6) null,
    last_failure         timestamp(6) null,
    consecutive_failures INT,
    last_heartbeat       timestamp(6) null,
    version              BIGINT       not null,
    priority             SMALLINT,
    PRIMARY KEY (task_name, task_instance),
    INDEX execution_time_idx (execution_time),
    INDEX last_heartbeat_idx (last_heartbeat),
    INDEX priority_execution_time_idx (priority desc, execution_time asc)
);

create table scheduled_logs
(
    id                   BIGINT       not null primary key,
    task_name            varchar(40)  not null,
    task_instance        varchar(40)  not null,
    task_data            blob,
    picked_by            varchar(50),
    time_started         timestamp(6) not null,
    time_finished        timestamp(6) not null,
    succeeded            BOOLEAN      not null,
    duration_ms          BIGINT       not null,
    exception_class      varchar(1000),
    exception_message    blob,
    exception_stacktrace blob,
    INDEX stl_started_idx (time_started),
    INDEX stl_task_name_idx (task_name),
    INDEX stl_exception_class_idx (exception_class)
)
