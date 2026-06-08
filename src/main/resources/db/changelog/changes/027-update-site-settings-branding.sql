--liquibase formatted sql

--changeset school:027-update-site-settings-branding
UPDATE site_settings
SET
    name         = 'MUNA School of Pine Hill',
    short_name   = 'MSPH',
    founded_year = '2026',
    address      = '400 Erial Rd, Pine Hill, NJ 08021',
    phone        = '856-484-6949',
    email        = 'info@munasph.org',
    base_url     = 'https://munasph.org'
WHERE id = 1
  AND (
    name = 'School Name'
    OR short_name = 'School'
    OR base_url = 'https://example.com'
    OR email = 'email@example.com'
  );
