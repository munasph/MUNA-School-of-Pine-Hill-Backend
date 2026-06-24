--liquibase formatted sql

--changeset cms:006-publish-status-enums-via-columns
-- Shared status column values: DRAFT, PUBLISHED, ARCHIVED

--changeset cms:006-events
CREATE TABLE IF NOT EXISTS events (
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

CREATE INDEX IF NOT EXISTS idx_events_start_at ON events (start_at DESC);

--changeset cms:006-news-posts
CREATE TABLE IF NOT EXISTS news_posts (
    id           BIGSERIAL PRIMARY KEY,
    slug         VARCHAR(200) NOT NULL,
    title        VARCHAR(300) NOT NULL,
    summary      VARCHAR(500),
    body         TEXT,
    image_url    VARCHAR(500),
    author       VARCHAR(200),
    status       VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    published_at TIMESTAMPTZ,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_news_posts_slug UNIQUE (slug)
);

--changeset cms:006-faculty-members
CREATE TABLE IF NOT EXISTS faculty_members (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    role_title  VARCHAR(200),
    department  VARCHAR(200),
    email       VARCHAR(200),
    phone       VARCHAR(50),
    bio         TEXT,
    image_url   VARCHAR(500),
    sort_order  INT          NOT NULL DEFAULT 0,
    status      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

--changeset cms:006-documents
CREATE TABLE IF NOT EXISTS documents (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(300) NOT NULL,
    description VARCHAR(500),
    category    VARCHAR(100),
    file_url    VARCHAR(500) NOT NULL,
    file_name   VARCHAR(300),
    sort_order  INT          NOT NULL DEFAULT 0,
    status      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

--changeset cms:006-gallery-items
CREATE TABLE IF NOT EXISTS gallery_items (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(300) NOT NULL,
    caption     VARCHAR(500),
    image_url   VARCHAR(500) NOT NULL,
    album       VARCHAR(100),
    sort_order  INT          NOT NULL DEFAULT 0,
    status      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

--changeset cms:006-faq-items
CREATE TABLE IF NOT EXISTS faq_items (
    id          BIGSERIAL PRIMARY KEY,
    question    VARCHAR(500) NOT NULL,
    answer      TEXT         NOT NULL,
    category    VARCHAR(100),
    sort_order  INT          NOT NULL DEFAULT 0,
    status      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

--changeset cms:006-media-assets
CREATE TABLE IF NOT EXISTS media_assets (
    id          BIGSERIAL PRIMARY KEY,
    file_name   VARCHAR(300) NOT NULL,
    url         VARCHAR(500) NOT NULL,
    mime_type   VARCHAR(100),
    size_bytes  BIGINT,
    alt_text    VARCHAR(300),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

--changeset cms:006-page-seo
CREATE TABLE IF NOT EXISTS page_seo (
    id              BIGSERIAL PRIMARY KEY,
    page_key        VARCHAR(100) NOT NULL,
    title           VARCHAR(300),
    description     VARCHAR(500),
    og_image_url    VARCHAR(500),
    updated_at      TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_page_seo_page_key UNIQUE (page_key)
);

--changeset cms:006-admin-users
CREATE TABLE IF NOT EXISTS admin_users (
    id            BIGSERIAL PRIMARY KEY,
    email         VARCHAR(200) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    display_name  VARCHAR(200),
    role          VARCHAR(20)  NOT NULL DEFAULT 'EDITOR',
    active        BOOLEAN      NOT NULL DEFAULT TRUE,
    last_login_at TIMESTAMPTZ,
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_admin_users_email UNIQUE (email)
);

--changeset cms:006-admission-notes
CREATE TABLE IF NOT EXISTS admission_notes (
    id             BIGSERIAL PRIMARY KEY,
    application_id BIGINT       NOT NULL REFERENCES admission_applications(id) ON DELETE CASCADE,
    body           TEXT         NOT NULL,
    author_email   VARCHAR(200),
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_admission_notes_application_id ON admission_notes (application_id);

--changeset cms:006-grade-intake-limits
CREATE TABLE IF NOT EXISTS grade_intake_limits (
    id                BIGSERIAL PRIMARY KEY,
    grade_key         VARCHAR(100) NOT NULL,
    academic_year     VARCHAR(20)  NOT NULL,
    max_applications  INT,
    waitlist_enabled  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_grade_intake_limits_grade_year UNIQUE (grade_key, academic_year)
);

--changeset cms:006-admission-documents
CREATE TABLE IF NOT EXISTS admission_documents (
    id             BIGSERIAL PRIMARY KEY,
    application_id BIGINT       NOT NULL REFERENCES admission_applications(id) ON DELETE CASCADE,
    doc_type       VARCHAR(100),
    file_name      VARCHAR(300) NOT NULL,
    file_url       VARCHAR(500) NOT NULL,
    uploaded_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

--changeset cms:006-admission-form-fields
CREATE TABLE IF NOT EXISTS admission_form_fields (
    id          BIGSERIAL PRIMARY KEY,
    field_key   VARCHAR(100) NOT NULL,
    label       VARCHAR(200) NOT NULL,
    required    BOOLEAN      NOT NULL DEFAULT FALSE,
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    sort_order  INT          NOT NULL DEFAULT 0,
    CONSTRAINT uq_admission_form_fields_field_key UNIQUE (field_key)
);

--changeset cms:006-inquiry-reply-templates
CREATE TABLE IF NOT EXISTS inquiry_reply_templates (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    subject     VARCHAR(300) NOT NULL,
    body        TEXT         NOT NULL,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

--changeset cms:006-email-campaigns
CREATE TABLE IF NOT EXISTS email_campaigns (
    id          BIGSERIAL PRIMARY KEY,
    subject     VARCHAR(300) NOT NULL,
    body        TEXT         NOT NULL,
    status      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    sent_at     TIMESTAMPTZ,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

--changeset cms:006-audit-logs
CREATE TABLE IF NOT EXISTS audit_logs (
    id          BIGSERIAL PRIMARY KEY,
    action      VARCHAR(50)  NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id   VARCHAR(100),
    actor_email VARCHAR(200),
    details     TEXT,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_audit_logs_created_at ON audit_logs (created_at DESC);

--changeset cms:006-notification-settings
CREATE TABLE IF NOT EXISTS notification_settings (
    id                          BIGINT PRIMARY KEY DEFAULT 1,
    email_on_new_admission      BOOLEAN NOT NULL DEFAULT TRUE,
    email_on_new_contact        BOOLEAN NOT NULL DEFAULT TRUE,
    admin_notification_email    VARCHAR(200),
    updated_at                  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_notification_settings_singleton CHECK (id = 1)
);

INSERT INTO notification_settings (id)
VALUES (1)
ON CONFLICT (id) DO NOTHING;

--changeset cms:006-analytics-settings
CREATE TABLE IF NOT EXISTS analytics_settings (
    id                   BIGINT PRIMARY KEY DEFAULT 1,
    enabled              BOOLEAN NOT NULL DEFAULT FALSE,
    ga_measurement_id    VARCHAR(100),
    updated_at           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_analytics_settings_singleton CHECK (id = 1)
);

INSERT INTO analytics_settings (id)
VALUES (1)
ON CONFLICT (id) DO NOTHING;

--changeset cms:006-announcement-scheduling
ALTER TABLE announcements
    ADD COLUMN IF NOT EXISTS starts_at TIMESTAMPTZ;
ALTER TABLE announcements
    ADD COLUMN IF NOT EXISTS ends_at TIMESTAMPTZ;
