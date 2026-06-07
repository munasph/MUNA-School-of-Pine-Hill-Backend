--liquibase formatted sql

--changeset school:024-add-admission-registration-fields
ALTER TABLE admission_applications ADD COLUMN IF NOT EXISTS first_name VARCHAR(100);
ALTER TABLE admission_applications ADD COLUMN IF NOT EXISTS last_name VARCHAR(100);
ALTER TABLE admission_applications ADD COLUMN IF NOT EXISTS street_address VARCHAR(200);
ALTER TABLE admission_applications ADD COLUMN IF NOT EXISTS city VARCHAR(100);
ALTER TABLE admission_applications ADD COLUMN IF NOT EXISTS state VARCHAR(50);
ALTER TABLE admission_applications ADD COLUMN IF NOT EXISTS zip VARCHAR(20);
ALTER TABLE admission_applications ADD COLUMN IF NOT EXISTS parent1_email VARCHAR(200);
ALTER TABLE admission_applications ADD COLUMN IF NOT EXISTS parent2_name VARCHAR(200);
ALTER TABLE admission_applications ADD COLUMN IF NOT EXISTS parent2_phone VARCHAR(30);
ALTER TABLE admission_applications ADD COLUMN IF NOT EXISTS parent2_email VARCHAR(200);

UPDATE admission_applications
SET first_name = full_name,
    last_name  = ''
WHERE first_name IS NULL;
