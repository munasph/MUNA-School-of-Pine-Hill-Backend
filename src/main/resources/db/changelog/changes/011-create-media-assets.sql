--liquibase formatted sql

--changeset school:011-create-media-assets
CREATE TABLE media_assets (
    id          BIGSERIAL PRIMARY KEY,
    file_name   VARCHAR(300) NOT NULL,
    url         VARCHAR(500) NOT NULL,
    mime_type   VARCHAR(100),
    size_bytes  BIGINT,
    alt_text    VARCHAR(300),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
