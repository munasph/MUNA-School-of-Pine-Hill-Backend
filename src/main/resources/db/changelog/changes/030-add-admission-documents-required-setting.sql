--liquibase formatted sql

--changeset school:030-add-admission-documents-required-setting
ALTER TABLE site_settings
    ADD COLUMN IF NOT EXISTS admission_documents_required BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN site_settings.admission_documents_required IS
    'When true, the public registration form shows document uploads and requires all mandatory documents.';
