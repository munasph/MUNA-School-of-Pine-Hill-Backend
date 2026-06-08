--liquibase formatted sql

--changeset school:025-admin-auth-workflow
ALTER TABLE admin_users
    ADD COLUMN IF NOT EXISTS approval_status VARCHAR(20) NOT NULL DEFAULT 'APPROVED',
    ADD COLUMN IF NOT EXISTS account_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    ADD COLUMN IF NOT EXISTS invited_by BIGINT REFERENCES admin_users(id),
    ADD COLUMN IF NOT EXISTS approved_by BIGINT REFERENCES admin_users(id),
    ADD COLUMN IF NOT EXISTS approved_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS invite_token_hash VARCHAR(128),
    ADD COLUMN IF NOT EXISTS invite_expires_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS failed_login_attempts INT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS lockout_until TIMESTAMPTZ;

UPDATE admin_users
SET approval_status = 'APPROVED',
    account_status = 'ACTIVE'
WHERE approval_status IS NULL OR account_status IS NULL;
