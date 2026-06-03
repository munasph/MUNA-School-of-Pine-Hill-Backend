--liquibase formatted sql

--changeset school:023-drop-tutorials-table
DROP TABLE IF EXISTS tutorials;
