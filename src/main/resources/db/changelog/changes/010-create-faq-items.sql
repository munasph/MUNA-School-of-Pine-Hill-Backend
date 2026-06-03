--liquibase formatted sql

--changeset school:010-create-faq-items
CREATE TABLE faq_items (
    id          BIGSERIAL PRIMARY KEY,
    question    VARCHAR(500) NOT NULL,
    answer      TEXT         NOT NULL,
    category    VARCHAR(100),
    sort_order  INT          NOT NULL DEFAULT 0,
    status      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
