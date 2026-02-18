CREATE TABLE documents
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    filename      VARCHAR(255) NOT NULL,
    original_name VARCHAR(500) NOT NULL,
    file_path     VARCHAR(1000) NOT NULL,
    file_size     BIGINT       NOT NULL,
    mime_type     VARCHAR(100) NOT NULL,
    created_by BIGINT              NOT NULL DEFAULT 0,
    created_at TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT,
    updated_at TIMESTAMP,
    deleted_by BIGINT,
    deleted_at TIMESTAMP,
);