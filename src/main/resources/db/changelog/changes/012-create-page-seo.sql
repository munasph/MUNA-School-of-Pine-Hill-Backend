--liquibase formatted sql

--changeset school:012-create-page-seo
CREATE TABLE page_seo (
    id              BIGSERIAL PRIMARY KEY,
    page_key        VARCHAR(100) NOT NULL,
    title           VARCHAR(300),
    description     VARCHAR(500),
    og_image_url    VARCHAR(500),
    updated_at      TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_page_seo_page_key UNIQUE (page_key)
);
