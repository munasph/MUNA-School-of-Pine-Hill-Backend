--liquibase formatted sql

--changeset school:014-create-grade-intake-limits
CREATE TABLE grade_intake_limits (
    id                BIGSERIAL PRIMARY KEY,
    grade_key         VARCHAR(100) NOT NULL,
    academic_year     VARCHAR(20)  NOT NULL,
    max_applications  INT,
    waitlist_enabled  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_grade_intake_limits_grade_year UNIQUE (grade_key, academic_year)
);
