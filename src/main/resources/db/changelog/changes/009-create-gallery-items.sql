--liquibase formatted sql

--changeset school:009-create-gallery-items
CREATE TABLE gallery_items (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(300) NOT NULL,
    caption     VARCHAR(500),
    image_url   VARCHAR(500) NOT NULL,
    album       VARCHAR(100),
    sort_order  INT          NOT NULL DEFAULT 0,
    status      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
