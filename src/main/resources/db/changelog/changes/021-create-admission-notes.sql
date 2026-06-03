--liquibase formatted sql

--changeset school:021-create-admission-notes
CREATE TABLE admission_notes (
    id             BIGSERIAL PRIMARY KEY,
    application_id BIGINT       NOT NULL REFERENCES admission_applications(id) ON DELETE CASCADE,
    body           TEXT         NOT NULL,
    author_email   VARCHAR(200),
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_admission_notes_application_id ON admission_notes (application_id);
