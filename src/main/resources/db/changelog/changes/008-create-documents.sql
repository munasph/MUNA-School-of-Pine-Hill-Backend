--liquibase formatted sql

--changeset school:008-create-documents
CREATE TABLE documents (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(300) NOT NULL,
    description VARCHAR(500),
    category    VARCHAR(100),
    file_url    VARCHAR(500) NOT NULL,
    file_name   VARCHAR(300),
    sort_order  INT          NOT NULL DEFAULT 0,
    status      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
