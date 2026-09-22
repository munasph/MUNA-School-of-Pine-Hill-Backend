--liquibase formatted sql
--changeset munasph:034-update-site-address-second-floor

UPDATE site_settings
SET address = '400 Erial Rd, Pine Hill, NJ 08021 (Second Floor)'
WHERE id = 1
  AND address IN (
    '400 Erial Rd, Pine Hill, NJ 08021',
    '400 Erial Rd, Pine Hill, NJ 08021 (Second Floor)'
  );
