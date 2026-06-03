--liquibase formatted sql

--changeset school:015-create-admission-form-fields
CREATE TABLE admission_form_fields (
    id          BIGSERIAL PRIMARY KEY,
    field_key   VARCHAR(100) NOT NULL,
    label       VARCHAR(200) NOT NULL,
    required    BOOLEAN      NOT NULL DEFAULT FALSE,
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    sort_order  INT          NOT NULL DEFAULT 0,
    CONSTRAINT uq_admission_form_fields_field_key UNIQUE (field_key)
);
