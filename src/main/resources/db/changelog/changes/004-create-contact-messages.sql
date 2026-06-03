--liquibase formatted sql

--changeset school:004-create-contact-messages
CREATE TABLE contact_messages (
    id           BIGSERIAL PRIMARY KEY,
    message_id   VARCHAR(64)  NOT NULL,
    name         VARCHAR(200) NOT NULL,
    email        VARCHAR(200) NOT NULL,
    subject      VARCHAR(300) NOT NULL,
    message      TEXT         NOT NULL,
    status       VARCHAR(20)  NOT NULL DEFAULT 'NEW',
    submitted_at TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_contact_messages_message_id UNIQUE (message_id)
);

CREATE INDEX idx_contact_messages_submitted_at
    ON contact_messages (submitted_at DESC);

CREATE INDEX idx_contact_messages_status_submitted_at
    ON contact_messages (status, submitted_at DESC);
