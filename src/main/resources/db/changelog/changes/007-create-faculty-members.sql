--liquibase formatted sql

--changeset school:007-create-faculty-members
CREATE TABLE faculty_members (
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
