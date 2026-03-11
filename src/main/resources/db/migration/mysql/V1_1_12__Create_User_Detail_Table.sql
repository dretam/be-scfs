-- Create user_detail table for storing internal user details
CREATE TABLE IF NOT EXISTS user_detail
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    user_id     BIGINT       NOT NULL UNIQUE,
    nama        VARCHAR(100) NULL,
    jabatan     INT          NULL,
    email       VARCHAR(200) NULL,
    area        VARCHAR(10)  NULL,
    job_title   VARCHAR(100) NULL,
    direktorat  VARCHAR(10)  NULL,
    sex         VARCHAR(10)  NULL,
    mobile      VARCHAR(20)  NULL,
    tgl_lahir   VARCHAR(255) NULL,
    cabang      VARCHAR(10)  NULL,
    branch      VARCHAR(10)  NULL,
    created_by  BIGINT       NOT NULL DEFAULT 0,
    created_at  DATETIME              DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT       NULL,
    updated_at  DATETIME     NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT       NULL,
    deleted_at  DATETIME     NULL,
    CONSTRAINT user_detail_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user_detail_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_detail_cabang FOREIGN KEY (cabang) REFERENCES branch (id_branch),
    CONSTRAINT fk_user_detail_branch FOREIGN KEY (branch) REFERENCES branch (id_branch)
);

CREATE INDEX idx_user_detail_user_id ON user_detail (user_id);
CREATE INDEX idx_user_detail_nama ON user_detail (nama);
CREATE INDEX idx_user_detail_email ON user_detail (email);
CREATE INDEX idx_user_detail_area ON user_detail (area);
CREATE INDEX idx_user_detail_direktorat ON user_detail (direktorat);
