-- Add document_id column to ocr_data table
ALTER TABLE ocr_data
ADD COLUMN document_id BIGINT,
ADD CONSTRAINT fk_ocr_data_document
FOREIGN KEY (document_id) REFERENCES documents(id);

-- Add index for better query performance
CREATE INDEX idx_ocr_data_document_id ON ocr_data(document_id);
