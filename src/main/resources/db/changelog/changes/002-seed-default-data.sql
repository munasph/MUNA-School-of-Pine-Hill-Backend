--liquibase formatted sql

--changeset cms:002-site-settings-default
INSERT INTO site_settings (
    id,
    name,
    short_name,
    founded_year,
    address,
    phone,
    email,
    office_hours,
    base_url
)
SELECT
    1,
    'School Name',
    'School',
    '0000',
    'Street Address, City, State, Country',
    'Phone Number',
    'email@example.com',
    'Office Hours Placeholder',
    'https://example.com'
WHERE NOT EXISTS (
    SELECT 1 FROM site_settings WHERE id = 1
);

--changeset cms:002-announcement-default
INSERT INTO announcements (
    emoji,
    title,
    subtitle,
    cta,
    href,
    active,
    created_at,
    updated_at
)
SELECT
    '🎓',
    'Announcement Title',
    'Short subtitle',
    'Learn More',
    '/admission',
    TRUE,
    NOW(),
    NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM announcements
);
