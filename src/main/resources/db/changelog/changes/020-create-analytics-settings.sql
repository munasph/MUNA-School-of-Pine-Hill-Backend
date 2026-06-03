--liquibase formatted sql

--changeset school:020-create-analytics-settings
CREATE TABLE analytics_settings (
    id                   BIGINT PRIMARY KEY DEFAULT 1,
    enabled              BOOLEAN NOT NULL DEFAULT FALSE,
    ga_measurement_id    VARCHAR(100),
    updated_at           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_analytics_settings_singleton CHECK (id = 1)
);
