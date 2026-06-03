--liquibase formatted sql

--changeset school:018-create-audit-logs
CREATE TABLE audit_logs (
    id          BIGSERIAL PRIMARY KEY,
    action      VARCHAR(50)  NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id   VARCHAR(100),
    actor_email VARCHAR(200),
    details     TEXT,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_audit_logs_created_at ON audit_logs (created_at DESC);
