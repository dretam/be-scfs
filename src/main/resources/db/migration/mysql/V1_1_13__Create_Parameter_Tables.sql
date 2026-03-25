-- MySQL Parameter Tables Migration

-- 1. Parameter Sumber Dana
CREATE TABLE parameter_sumber_dana
(
    code        CHAR(1) PRIMARY KEY,
    value       VARCHAR(50) NOT NULL,
    created_by  BIGINT      NOT NULL DEFAULT 0,
    created_at  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT      NULL,
    updated_at  DATETIME    NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT      NULL,
    deleted_at  DATETIME    NULL
);

-- 2. Parameter Jenis Perpanjangan
CREATE TABLE parameter_jenis_perpanjangan
(
    code        CHAR(1) PRIMARY KEY,
    value       VARCHAR(50) NOT NULL,
    created_by  BIGINT      NOT NULL DEFAULT 0,
    created_at  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT      NULL,
    updated_at  DATETIME    NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT      NULL,
    deleted_at  DATETIME    NULL
);

-- 3. Parameter Metode Pembayaran Bunga
CREATE TABLE parameter_metode_pembayaran_bunga
(
    code        CHAR(1) PRIMARY KEY,
    value       VARCHAR(50) NOT NULL,
    created_by  BIGINT      NOT NULL DEFAULT 0,
    created_at  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT      NULL,
    updated_at  DATETIME    NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT      NULL,
    deleted_at  DATETIME    NULL
);

-- 4. Parameter Jenis Transfer
CREATE TABLE parameter_jenis_transfer
(
    code        INT PRIMARY KEY,
    value       VARCHAR(50) NOT NULL,
    created_by  BIGINT      NOT NULL DEFAULT 0,
    created_at  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT      NULL,
    updated_at  DATETIME    NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT      NULL,
    deleted_at  DATETIME    NULL
);

-- 5. Parameter Jenis Transaksi SKN
CREATE TABLE parameter_jenis_transaksi_skn
(
    code        INT PRIMARY KEY,
    value       VARCHAR(50) NOT NULL,
    created_by  BIGINT      NOT NULL DEFAULT 0,
    created_at  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT      NULL,
    updated_at  DATETIME    NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT      NULL,
    deleted_at  DATETIME    NULL
);

-- 6. Parameter Jenis Transaksi RTGS
CREATE TABLE parameter_jenis_transaksi_rtgs
(
    code        INT PRIMARY KEY,
    value       VARCHAR(100) NOT NULL,
    created_by  BIGINT       NOT NULL DEFAULT 0,
    created_at  DATETIME              DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT       NULL,
    updated_at  DATETIME     NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT       NULL,
    deleted_at  DATETIME     NULL
);

-- 7. Parameter Jenis Nasabah Penerima
CREATE TABLE parameter_jenis_nasabah_penerima
(
    code        INT PRIMARY KEY,
    value       VARCHAR(50) NOT NULL,
    created_by  BIGINT      NOT NULL DEFAULT 0,
    created_at  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT      NULL,
    updated_at  DATETIME    NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT      NULL,
    deleted_at  DATETIME    NULL
);

-- 8. Parameter Status Kependudukan Penerima
CREATE TABLE parameter_status_kependudukan_penerima
(
    code        INT PRIMARY KEY,
    value       VARCHAR(50) NOT NULL,
    created_by  BIGINT      NOT NULL DEFAULT 0,
    created_at  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT      NULL,
    updated_at  DATETIME    NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT      NULL,
    deleted_at  DATETIME    NULL
);

-- 9. Parameter Metode Pembayaran Pokok
CREATE TABLE parameter_metode_pembayaran_pokok
(
    code        CHAR(1) PRIMARY KEY,
    value       VARCHAR(50) NOT NULL,
    created_by  BIGINT      NOT NULL DEFAULT 0,
    created_at  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT      NULL,
    updated_at  DATETIME    NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT      NULL,
    deleted_at  DATETIME    NULL
);

-- 10. Parameter Approver Biaya Materai
CREATE TABLE parameter_approver_biaya_materai
(
    code        CHAR(1) PRIMARY KEY,
    value       VARCHAR(50) NOT NULL,
    created_by  BIGINT      NOT NULL DEFAULT 0,
    created_at  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT      NULL,
    updated_at  DATETIME    NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT      NULL,
    deleted_at  DATETIME    NULL
);

-- 11. Parameter Automatic Transfer
CREATE TABLE parameter_automatic_transfer
(
    code        CHAR(1) PRIMARY KEY,
    value       VARCHAR(50) NOT NULL,
    created_by  BIGINT      NOT NULL DEFAULT 0,
    created_at  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_by  BIGINT      NULL,
    updated_at  DATETIME    NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_by  BIGINT      NULL,
    deleted_at  DATETIME    NULL
);

-- Create indexes for better performance
CREATE INDEX idx_parameter_sumber_dana_value ON parameter_sumber_dana (value);
CREATE INDEX idx_parameter_jenis_perpanjangan_value ON parameter_jenis_perpanjangan (value);
CREATE INDEX idx_parameter_metode_pembayaran_bunga_value ON parameter_metode_pembayaran_bunga (value);
CREATE INDEX idx_parameter_jenis_transfer_value ON parameter_jenis_transfer (value);
CREATE INDEX idx_parameter_jenis_transaksi_skn_value ON parameter_jenis_transaksi_skn (value);
CREATE INDEX idx_parameter_jenis_transaksi_rtgs_value ON parameter_jenis_transaksi_rtgs (value);
CREATE INDEX idx_parameter_jenis_nasabah_penerima_value ON parameter_jenis_nasabah_penerima (value);
CREATE INDEX idx_parameter_status_kependudukan_penerima_value ON parameter_status_kependudukan_penerima (value);
CREATE INDEX idx_parameter_metode_pembayaran_pokok_value ON parameter_metode_pembayaran_pokok (value);
CREATE INDEX idx_parameter_approver_biaya_materai_value ON parameter_approver_biaya_materai (value);
CREATE INDEX idx_parameter_automatic_transfer_value ON parameter_automatic_transfer (value);

-- Insert default data for Parameter Sumber Dana
INSERT INTO parameter_sumber_dana (code, value) VALUES
('D', 'OVERBOOKING'),
('F', 'TRANSFER');

-- Insert default data for Parameter Jenis Perpanjangan
INSERT INTO parameter_jenis_perpanjangan (code, value) VALUES
('N', 'TIDAK DIPERPANJANG'),
('Y', 'ARO POKOK'),
('A', 'ARO POKOK + BUNGA');

-- Insert default data for Parameter Metode Pembayaran Bunga
INSERT INTO parameter_metode_pembayaran_bunga (code, value) VALUES
('D', 'OVERBOOKING'),
('F', 'TRANSFER'),
('A', 'TAMBAH KE POKOK');

-- Insert default data for Parameter Jenis Transfer
INSERT INTO parameter_jenis_transfer (code, value) VALUES
(1, 'SKN'),
(2, 'RTGS');

-- Insert default data for Parameter Jenis Transaksi SKN
INSERT INTO parameter_jenis_transaksi_skn (code, value) VALUES
(50, 'NOTA KREDIT');

-- Insert default data for Parameter Jenis Transaksi RTGS
INSERT INTO parameter_jenis_transaksi_rtgs (code, value) VALUES
(100, 'ANTAR PESERTA - UTK NASABAH'),
(101, 'ANTAR PESERTA - NASABAH NON ACCT');

-- Insert default data for Parameter Jenis Nasabah Penerima
INSERT INTO parameter_jenis_nasabah_penerima (code, value) VALUES
(1, 'PERORANGAN'),
(2, 'NON PERORANGAN'),
(3, 'PEMERINTAH');

-- Insert default data for Parameter Status Kependudukan Penerima
INSERT INTO parameter_status_kependudukan_penerima (code, value) VALUES
(1, 'PENDUDUK'),
(2, 'BUKAN PENDUDUK');

-- Insert default data for Parameter Metode Pembayaran Pokok
INSERT INTO parameter_metode_pembayaran_pokok (code, value) VALUES
('D', 'OVERBOOKING'),
('F', 'TRANSFER');

-- Insert default data for Parameter Approver Biaya Materai
INSERT INTO parameter_approver_biaya_materai (code, value) VALUES
('Y', 'YA'),
('N', 'TIDAK');

-- Insert default data for Parameter Automatic Transfer
INSERT INTO parameter_automatic_transfer (code, value) VALUES
('N', 'TIDAK'),
('Y', 'YA (AT ONCE)'),
('S', 'YA (SPLITTING)');
