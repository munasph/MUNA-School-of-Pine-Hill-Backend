--liquibase formatted sql

--changeset school:019-create-notification-settings
CREATE TABLE notification_settings (
    id                          BIGINT PRIMARY KEY DEFAULT 1,
    email_on_new_admission      BOOLEAN NOT NULL DEFAULT TRUE,
    email_on_new_contact        BOOLEAN NOT NULL DEFAULT TRUE,
    admin_notification_email    VARCHAR(200),
    updated_at                  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_notification_settings_singleton CHECK (id = 1)
);
