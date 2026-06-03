--liquibase formatted sql

--changeset cms:004-ensure-tutorials-table
CREATE TABLE IF NOT EXISTS tutorials (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255),
    description TEXT,
    published   BOOLEAN NOT NULL DEFAULT FALSE
);

--changeset cms:004-ensure-contact-messages-table
CREATE TABLE IF NOT EXISTS contact_messages (
    id           BIGSERIAL PRIMARY KEY,
    message_id   VARCHAR(64)  NOT NULL,
    name         VARCHAR(200) NOT NULL,
    email        VARCHAR(200) NOT NULL,
    subject      VARCHAR(300) NOT NULL,
    message      TEXT         NOT NULL,
    submitted_at TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_contact_messages_message_id UNIQUE (message_id)
);

CREATE INDEX IF NOT EXISTS idx_contact_messages_submitted_at
    ON contact_messages (submitted_at DESC);
