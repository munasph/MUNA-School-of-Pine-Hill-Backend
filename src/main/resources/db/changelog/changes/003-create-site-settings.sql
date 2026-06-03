--liquibase formatted sql

--changeset school:003-create-site-settings
CREATE TABLE site_settings (
    id               BIGINT PRIMARY KEY,
    name             VARCHAR(200) NOT NULL DEFAULT 'School Name',
    short_name       VARCHAR(100) NOT NULL DEFAULT 'School',
    founded_year     VARCHAR(10)  DEFAULT '0000',
    address          VARCHAR(500) DEFAULT 'Street Address, City, State, Country',
    phone            VARCHAR(50)  DEFAULT 'Phone Number',
    email            VARCHAR(200) DEFAULT 'email@example.com',
    office_hours     VARCHAR(200) DEFAULT 'Office Hours Placeholder',
    base_url         VARCHAR(500) DEFAULT 'https://example.com',
    admissions_open  BOOLEAN      NOT NULL DEFAULT TRUE
);
