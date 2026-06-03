--liquibase formatted sql

--changeset school:016-create-inquiry-reply-templates
CREATE TABLE inquiry_reply_templates (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    subject     VARCHAR(300) NOT NULL,
    body        TEXT         NOT NULL,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
