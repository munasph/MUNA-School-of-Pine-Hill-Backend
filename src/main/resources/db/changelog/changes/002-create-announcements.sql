--liquibase formatted sql

--changeset school:002-create-announcements
CREATE TABLE announcements (
    id          BIGSERIAL PRIMARY KEY,
    emoji       VARCHAR(16),
    title       VARCHAR(300) NOT NULL,
    subtitle    VARCHAR(500),
    cta         VARCHAR(100),
    href        VARCHAR(500),
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    starts_at   TIMESTAMPTZ,
    ends_at     TIMESTAMPTZ,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_announcements_active_updated_at
    ON announcements (active, updated_at DESC);
