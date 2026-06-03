--liquibase formatted sql

--changeset school:022-create-admission-documents
CREATE TABLE admission_documents (
    id             BIGSERIAL PRIMARY KEY,
    application_id BIGINT       NOT NULL REFERENCES admission_applications(id) ON DELETE CASCADE,
    doc_type       VARCHAR(100),
    file_name      VARCHAR(300) NOT NULL,
    file_url       VARCHAR(500) NOT NULL,
    uploaded_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
