--liquibase formatted sql

--changeset school:001-create-admission-applications
CREATE TABLE admission_applications (
    id              BIGSERIAL PRIMARY KEY,
    application_id  VARCHAR(64)  NOT NULL,
    full_name       VARCHAR(200) NOT NULL,
    dob             DATE         NOT NULL,
    class_applying  VARCHAR(100) NOT NULL,
    gender          VARCHAR(20)  NOT NULL,
    parent_name     VARCHAR(200) NOT NULL,
    parent_phone    VARCHAR(30)  NOT NULL,
    status          VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    submitted_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_admission_applications_application_id UNIQUE (application_id)
);

CREATE INDEX idx_admission_applications_submitted_at
    ON admission_applications (submitted_at DESC);

CREATE INDEX idx_admission_applications_status
    ON admission_applications (status);
