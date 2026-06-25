--liquibase formatted sql

--changeset school:031-add-admission-required-document-types
ALTER TABLE site_settings
    ADD COLUMN IF NOT EXISTS admission_required_document_types TEXT NOT NULL DEFAULT '[]';

COMMENT ON COLUMN site_settings.admission_required_document_types IS
    'JSON array of admission document type codes enabled on the public registration form.';

-- Migrate legacy all-or-nothing toggle to the eight core required documents.
UPDATE site_settings
SET admission_required_document_types = '["BIRTH_CERTIFICATE","SOCIAL_SECURITY_CARD","PARENT_GUARDIAN_ID","EMERGENCY_CONTACT_ID","PHYSICAL_EXAM","DENTAL_EXAM","IMMUNIZATION","TAX_1040"]'
WHERE admission_documents_required = TRUE
  AND admission_required_document_types = '[]';
