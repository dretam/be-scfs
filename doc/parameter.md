-- 1. SUMBER DANA
CREATE TABLE parameter_sumber_dana (
code CHAR(1) PRIMARY KEY,
value VARCHAR(50)
);
INSERT INTO parameter_sumber_dana (code, value) VALUES
('D', 'OVERBOOKING'),
('F', 'TRANSFER');

-- 2. JENIS PERPANJANGAN
CREATE TABLE parameter_jenis_perpanjangan (
code CHAR(1) PRIMARY KEY,
value VARCHAR(50)
);
INSERT INTO parameter_jenis_perpanjangan (code, value) VALUES
('N', 'TIDAK DIPERPANJANG'),
('Y', 'ARO POKOK'),
('A', 'ARO POKOK + BUNGA');

-- 3. METODE PEMBAYARAN BUNGA
CREATE TABLE parameter_metode_pembayaran_bunga (
code CHAR(1) PRIMARY KEY,
value VARCHAR(50)
);
INSERT INTO parameter_metode_pembayaran_bunga (code, value) VALUES
('D', 'OVERBOOKING'),
('F', 'TRANSFER'),
('A', 'TAMBAH KE POKOK');

-- 4. JENIS TRANSFER
CREATE TABLE parameter_jenis_transfer (
code INT PRIMARY KEY,
value VARCHAR(50)
);
INSERT INTO parameter_jenis_transfer (code, value) VALUES
(1, 'SKN'),
(2, 'RTGS');

-- 5. JENIS TRANSAKSI SKN
CREATE TABLE parameter_jenis_transaksi_skn (
code INT PRIMARY KEY,
value VARCHAR(50)
);
INSERT INTO parameter_jenis_transaksi_skn (code, value) VALUES
(50, 'NOTA KREDIT');

-- 6. JENIS TRANSAKSI RTGS
CREATE TABLE parameter_jenis_transaksi_rtgs (
code INT PRIMARY KEY,
value VARCHAR(100)
);
INSERT INTO parameter_jenis_transaksi_rtgs (code, value) VALUES
(100, 'ANTAR PESERTA - UTK NASABAH'),
(101, 'ANTAR PESERTA - NASABAH NON ACCT');

-- 7. JENIS NASABAH PENERIMA
CREATE TABLE parameter_jenis_nasabah_penerima (
code INT PRIMARY KEY,
value VARCHAR(50)
);
INSERT INTO parameter_jenis_nasabah_penerima (code, value) VALUES
(1, 'PERORANGAN'),
(2, 'NON PERORANGAN'),
(3, 'PEMERINTAH');

-- 8. STATUS KEPENDUDUKAN PENERIMA
CREATE TABLE parameter_status_kependudukan_penerima (
code INT PRIMARY KEY,
value VARCHAR(50)
);
INSERT INTO parameter_status_kependudukan_penerima (code, value) VALUES
(1, 'PENDUDUK'),
(2, 'BUKAN PENDUDUK');

-- 9. METODE PEMBAYARAN POKOK
CREATE TABLE parameter_metode_pembayaran_pokok (
code CHAR(1) PRIMARY KEY,
value VARCHAR(50)
);
INSERT INTO parameter_metode_pembayaran_pokok (code, value) VALUES
('D', 'OVERBOOKING'),
('F', 'TRANSFER');

-- 10. SEBAGAI APPROVER, BIAYA TRANSFER, DAN BIAYA MATERAI
CREATE TABLE parameter_approver_biaya_materai (
code CHAR(1) PRIMARY KEY,
value VARCHAR(50)
);
INSERT INTO parameter_approver_biaya_materai (code, value) VALUES
('Y', 'YA'),
('N', 'TIDAK');

-- 11. AUTOMATIC TRANSFER
CREATE TABLE parameter_automatic_transfer (
code CHAR(1) PRIMARY KEY,
value VARCHAR(50)
);
INSERT INTO parameter_automatic_transfer (code, value) VALUES
('N', 'TIDAK'),
('Y', 'YA (AT ONCE)'),
('S', 'YA (SPLITTING)');