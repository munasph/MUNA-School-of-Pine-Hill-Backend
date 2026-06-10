--liquibase formatted sql

--changeset school:028-add-announcement-body
ALTER TABLE announcements ADD COLUMN body TEXT;
