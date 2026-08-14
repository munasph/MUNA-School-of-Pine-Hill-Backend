--liquibase formatted sql

--changeset school:032-update-site-email-admin
UPDATE site_settings
SET email = 'admin@munasph.org'
WHERE id = 1
  AND email = 'info@munasph.org';
