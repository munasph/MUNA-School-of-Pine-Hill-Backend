--liquibase formatted sql

--changeset cms:001-admission-applications
--preconditions onFail:MARK_RAN
--precondition-not tableExists tableName=admission_applications
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

--changeset cms:001-announcements
--preconditions onFail:MARK_RAN
--precondition-not tableExists tableName=announcements
CREATE TABLE announcements (
    id          BIGSERIAL PRIMARY KEY,
    emoji       VARCHAR(16),
    title       VARCHAR(300) NOT NULL,
    subtitle    VARCHAR(500),
    cta         VARCHAR(100),
    href        VARCHAR(500),
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_announcements_active_updated_at
    ON announcements (active, updated_at DESC);

--changeset cms:001-site-settings
--preconditions onFail:MARK_RAN
--precondition-not tableExists tableName=site_settings
CREATE TABLE site_settings (
    id            BIGINT PRIMARY KEY,
    name          VARCHAR(200) NOT NULL DEFAULT 'School Name',
    short_name    VARCHAR(100) NOT NULL DEFAULT 'School',
    founded_year  VARCHAR(10)  DEFAULT '0000',
    address       VARCHAR(500) DEFAULT 'Street Address, City, State, Country',
    phone         VARCHAR(50)  DEFAULT 'Phone Number',
    email         VARCHAR(200) DEFAULT 'email@example.com',
    office_hours  VARCHAR(200) DEFAULT 'Office Hours Placeholder',
    base_url      VARCHAR(500) DEFAULT 'https://example.com'
);

--changeset cms:001-tutorials
--preconditions onFail:MARK_RAN
--precondition-not tableExists tableName=tutorials
CREATE TABLE tutorials (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255),
    description TEXT,
    published   BOOLEAN NOT NULL DEFAULT FALSE
);
