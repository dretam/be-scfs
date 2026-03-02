DROP TABLE IF EXISTS internal_user;

CREATE TABLE internal_user
(
    user_name   varchar(15) NOT NULL,
    nama        varchar(100) NULL,
    join_date   date NULL,
    jabatan     int4 NULL,
    approval1   varchar(10) NULL,
    approval2   varchar(10) NULL,
    cabang      varchar(10) NULL,
    last_login  timestamp NULL,
    update_pass timestamp NULL,
    status      int4 DEFAULT 1 NULL,
    count       int4 DEFAULT 0 NULL,
    email       varchar(200) NULL,
    branch      varchar(11) NOT NULL,
    area        varchar(10) NULL,
    job_title   varchar(100) NULL,
    direktorat  varchar(100) NULL,
    sex         varchar(10) NULL,
    employee    varchar(2) NULL,
    mobile      varchar(20) NULL,
    "password"  varchar(50) NULL,
    ext_office  varchar(50) NULL,
    device_id   varchar(255) NULL,
    tgl_lahir   varchar(255) NULL,
    pangkat     varchar(255) NULL,
    session_id  varchar(255) NULL,
    unit_kerja  varchar(255) NULL,
    CONSTRAINT tb_user_pkey PRIMARY KEY (user_name)
);
CREATE INDEX approval1 ON internal_user USING btree (approval1);
CREATE INDEX approval2 ON internal_user USING btree (approval2);
CREATE INDEX cabang ON internal_user USING btree (cabang);
CREATE INDEX jabatan ON internal_user USING btree (jabatan);
CREATE UNIQUE INDEX user_name ON internal_user USING btree (user_name);