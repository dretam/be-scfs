CREATE TABLE IF NOT EXISTS internal_user
(
    user_name   VARCHAR(15) NOT NULL,
    nama        VARCHAR(100) NULL,
    join_date   DATE NULL,
    jabatan     INT NULL,
    approval1   VARCHAR(10) NULL,
    approval2   VARCHAR(10) NULL,
    cabang      VARCHAR(10) NULL,
    last_login  DATETIME NULL,
    update_pass DATETIME NULL,
    status      INT DEFAULT 1 NULL,
    count       INT DEFAULT 0 NULL,
    email       VARCHAR(200) NULL,
    branch      VARCHAR(11) NOT NULL,
    area        VARCHAR(10) NULL,
    job_title   VARCHAR(100) NULL,
    direktorat  VARCHAR(100) NULL,
    sex         VARCHAR(10) NULL,
    employee    VARCHAR(2) NULL,
    mobile      VARCHAR(20) NULL,
    password    VARCHAR(50) NULL,
    ext_office  VARCHAR(50) NULL,
    device_id   VARCHAR(255) NULL,
    tgl_lahir   VARCHAR(255) NULL,
    pangkat     VARCHAR(255) NULL,
    session_id  VARCHAR(255) NULL,
    unit_kerja  VARCHAR(255) NULL,
    CONSTRAINT tb_user_pkey PRIMARY KEY (user_name)
);

CREATE INDEX idx_internal_user_approval1 ON internal_user (approval1);
CREATE INDEX idx_internal_user_approval2 ON internal_user (approval2);
CREATE INDEX idx_internal_user_cabang ON internal_user (cabang);
CREATE INDEX idx_internal_user_jabatan ON internal_user (jabatan);
CREATE UNIQUE INDEX idx_internal_user_user_name ON internal_user (user_name);
