--liquibase formatted sql

--changeset school:029-create-magic-link-tokens
-- Passwordless "magic link" login tokens. Stored as SHA-256 hashes, short-lived, single-use.
CREATE TABLE magic_link_tokens (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT       NOT NULL REFERENCES portal_users(id) ON DELETE CASCADE,
    token_hash  VARCHAR(128) NOT NULL,
    expires_at  TIMESTAMPTZ  NOT NULL,
    used_at     TIMESTAMPTZ,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_magic_link_token_hash UNIQUE (token_hash)
);

CREATE INDEX idx_magic_link_tokens_user_id ON magic_link_tokens (user_id);
