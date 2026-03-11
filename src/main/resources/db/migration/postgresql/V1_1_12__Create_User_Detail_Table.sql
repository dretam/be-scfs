-- Create user_detail table for storing internal user details
CREATE TABLE IF NOT EXISTS user_detail
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT       NOT NULL UNIQUE,
    nama        VARCHAR(255) NULL,
    jabatan     INT          NULL,
    email       VARCHAR(255) NULL,
    area        VARCHAR(255) NULL,
    job_title   VARCHAR(255) NULL,
    direktorat  VARCHAR(255) NULL,
    sex         VARCHAR(255) NULL,
    mobile      VARCHAR(255) NULL,
    tgl_lahir   VARCHAR(255) NULL,
    cabang      VARCHAR(255) NULL,
    branch      VARCHAR(255) NULL,
    created_by  BIGINT       NOT NULL DEFAULT 0,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT       NULL,
    updated_at  TIMESTAMP    NULL,
    deleted_by  BIGINT       NULL,
    deleted_at  TIMESTAMP    NULL,
    CONSTRAINT fk_user_detail_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_detail_cabang FOREIGN KEY (cabang) REFERENCES branch (id_branch),
    CONSTRAINT fk_user_detail_branch FOREIGN KEY (branch) REFERENCES branch (id_branch)
);

CREATE INDEX idx_user_detail_user_id ON user_detail (user_id);
CREATE INDEX idx_user_detail_nama ON user_detail (nama);
CREATE INDEX idx_user_detail_email ON user_detail (email);
CREATE INDEX idx_user_detail_area ON user_detail (area);
CREATE INDEX idx_user_detail_direktorat ON user_detail (direktorat);

COMMENT ON TABLE user_detail IS 'Stores detailed information for internal users';
COMMENT ON COLUMN user_detail.user_id IS 'Reference to users table';
COMMENT ON COLUMN user_detail.nama IS 'Full name';
COMMENT ON COLUMN user_detail.jabatan IS 'Position/Jabatan ID';
COMMENT ON COLUMN user_detail.email IS 'Email address';
COMMENT ON COLUMN user_detail.area IS 'Area code';
COMMENT ON COLUMN user_detail.job_title IS 'Job title';
COMMENT ON COLUMN user_detail.direktorat IS 'Direktorat code';
COMMENT ON COLUMN user_detail.sex IS 'Gender';
COMMENT ON COLUMN user_detail.mobile IS 'Mobile phone number';
COMMENT ON COLUMN user_detail.tgl_lahir IS 'Date of birth';
COMMENT ON COLUMN user_detail.cabang IS 'Branch (cabang) reference';
COMMENT ON COLUMN user_detail.branch IS 'Branch reference';
