CREATE TABLE IF NOT EXISTS branch
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    id_branch   VARCHAR(10)    NOT NULL,
    branch_name VARCHAR(255)   NOT NULL,
    flag_del    INT DEFAULT 0 NOT NULL,
    category    VARCHAR(100) NULL,
    regional    VARCHAR(100) NULL,
    address     TEXT NULL,
    area        VARCHAR(100) NULL,
    direktorat  VARCHAR(100) NULL,
    mod_id      INT NULL,
    telepon     VARCHAR(20) NULL,
    faximile    VARCHAR(20) NULL,
    singkatan   VARCHAR(50) NULL,
    CONSTRAINT branch_pkey PRIMARY KEY (id)
);
