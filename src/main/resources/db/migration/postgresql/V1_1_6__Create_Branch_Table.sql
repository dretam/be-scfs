DROP TABLE IF EXISTS branch;

CREATE TABLE branch
(
    id          bigserial      NOT NULL,
    id_branch   varchar(10)    NOT NULL,
    branch_name varchar(255)   NOT NULL,
    flag_del    int8 DEFAULT 0 NOT NULL,
    category    varchar(100) NULL,
    regional    varchar(100) NULL,
    address     text NULL,
    area        varchar(100) NULL,
    direktorat  varchar(100) NULL,
    mod_id      int4 NULL,
    telepon     varchar(20) NULL,
    faximile    varchar(20) NULL,
    singkatan   varchar(50) NULL,
    CONSTRAINT branch_pkey PRIMARY KEY (id)
);