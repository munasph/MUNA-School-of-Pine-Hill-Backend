--liquibase formatted sql

--changeset school:024-create-portal-users
CREATE TABLE portal_users (
    id                    BIGSERIAL PRIMARY KEY,
    email                 VARCHAR(200) NOT NULL,
    password_hash         VARCHAR(255) NOT NULL,
    full_name             VARCHAR(200) NOT NULL,
    role                  VARCHAR(20)  NOT NULL DEFAULT 'PARENT',
    email_verified        BOOLEAN      NOT NULL DEFAULT FALSE,
    active                BOOLEAN      NOT NULL DEFAULT TRUE,
    failed_login_attempts INT          NOT NULL DEFAULT 0,
    lockout_until         TIMESTAMPTZ,
    mfa_enabled           BOOLEAN      NOT NULL DEFAULT FALSE,
    mfa_secret            VARCHAR(64),
    last_login_at         TIMESTAMPTZ,
    created_at            TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_portal_users_email UNIQUE (email)
);
