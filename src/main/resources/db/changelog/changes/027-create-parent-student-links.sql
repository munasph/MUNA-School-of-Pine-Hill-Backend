--liquibase formatted sql

--changeset school:027-create-parent-student-links
-- Links a portal user (parent/student) to an admission application record.
-- Access to a student's data requires an APPROVED link, granted by an admin.
CREATE TABLE parent_student_links (
    id             BIGSERIAL PRIMARY KEY,
    portal_user_id BIGINT       NOT NULL REFERENCES portal_users(id) ON DELETE CASCADE,
    application_id BIGINT       NOT NULL REFERENCES admission_applications(id) ON DELETE CASCADE,
    relationship   VARCHAR(50)  NOT NULL DEFAULT 'PARENT',
    status         VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_parent_student_link UNIQUE (portal_user_id, application_id)
);

CREATE INDEX idx_parent_student_links_user ON parent_student_links (portal_user_id);
