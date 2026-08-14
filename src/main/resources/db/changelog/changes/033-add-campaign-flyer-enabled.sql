--liquibase formatted sql

--changeset school:033-add-campaign-flyer-enabled
ALTER TABLE site_settings
    ADD COLUMN IF NOT EXISTS campaign_flyer_enabled BOOLEAN NOT NULL DEFAULT TRUE;

COMMENT ON COLUMN site_settings.campaign_flyer_enabled IS
    'When true, the public site auto-shows the open-house/campaign flyer popup on each visit.';
