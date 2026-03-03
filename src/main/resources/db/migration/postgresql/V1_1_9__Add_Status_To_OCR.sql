-- Create status enum type for OCR data
CREATE TYPE ocr_status AS ENUM ('PENDING', 'APPROVED', 'REJECTED');

-- Add status column to ocr_data table
ALTER TABLE ocr_data
ADD COLUMN status ocr_status NOT NULL DEFAULT 'PENDING';

-- Add index for status filtering
CREATE INDEX idx_ocr_data_status ON ocr_data(status);

-- Add comment for documentation
COMMENT ON COLUMN ocr_data.status IS 'OCR data status: PENDING, APPROVED, REJECTED';
