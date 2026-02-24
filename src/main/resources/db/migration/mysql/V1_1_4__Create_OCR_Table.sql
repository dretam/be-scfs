CREATE TABLE ocr_data
(
    id                              BIGINT AUTO_INCREMENT PRIMARY KEY,

    atas_nama                       VARCHAR(255) NOT NULL,
    nominal                         VARCHAR(100),
    jangka_waktu                    VARCHAR(100),
    periode                         VARCHAR(100),
    rate                            VARCHAR(50),
    alokasi                         VARCHAR(255),

    nama_rekening_tujuan_pencairan  VARCHAR(255),
    nomor_rekening_tujuan_pencairan VARCHAR(100),
    nomor_rekening_pengirim         VARCHAR(100),
    nomor_rekening_placement        VARCHAR(100),

    -- Audit fields
    created_by BIGINT              NOT NULL DEFAULT 0,
    created_at DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT,
    updated_at DATETIME            NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by BIGINT,
    deleted_at DATETIME            NULL
);
