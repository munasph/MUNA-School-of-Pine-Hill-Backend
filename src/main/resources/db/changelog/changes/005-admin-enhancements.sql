--liquibase formatted sql

--changeset cms:005-site-settings-admissions-open
ALTER TABLE site_settings
    ADD COLUMN IF NOT EXISTS admissions_open BOOLEAN NOT NULL DEFAULT TRUE;

--changeset cms:005-contact-messages-status
ALTER TABLE contact_messages
    ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'NEW';

CREATE INDEX IF NOT EXISTS idx_contact_messages_status_submitted_at
    ON contact_messages (status, submitted_at DESC);
