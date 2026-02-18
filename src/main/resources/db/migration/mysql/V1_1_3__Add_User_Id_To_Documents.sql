ALTER TABLE documents ADD COLUMN user_id BIGINT;
UPDATE documents SET user_id = created_by WHERE user_id IS NULL;