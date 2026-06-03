--liquibase formatted sql

--changeset school:005-create-events
CREATE TABLE events (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(300) NOT NULL,
    description TEXT,
    location    VARCHAR(300),
    start_at    TIMESTAMPTZ  NOT NULL,
    end_at      TIMESTAMPTZ,
    all_day     BOOLEAN      NOT NULL DEFAULT FALSE,
    status      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_events_start_at ON events (start_at DESC);
