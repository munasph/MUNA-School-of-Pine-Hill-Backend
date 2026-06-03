--liquibase formatted sql

--changeset school:017-create-email-campaigns
CREATE TABLE email_campaigns (
    id          BIGSERIAL PRIMARY KEY,
    subject     VARCHAR(300) NOT NULL,
    body        TEXT         NOT NULL,
    status      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    sent_at     TIMESTAMPTZ,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
