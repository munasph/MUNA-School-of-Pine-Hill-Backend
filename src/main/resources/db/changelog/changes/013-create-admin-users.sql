--liquibase formatted sql

--changeset school:013-create-admin-users
CREATE TABLE admin_users (
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
