-- Add status column to ocr_data table
ALTER TABLE ocr_data
ADD COLUMN status ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL DEFAULT 'PENDING' AFTER nomor_rekening_placement;

-- Add index for status filtering
CREATE INDEX idx_ocr_data_status ON ocr_data(status);

-- Add comment for documentation
ALTER TABLE ocr_data MODIFY COLUMN status ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL DEFAULT 'PENDING' COMMENT 'OCR data status: PENDING, APPROVED, REJECTED';
