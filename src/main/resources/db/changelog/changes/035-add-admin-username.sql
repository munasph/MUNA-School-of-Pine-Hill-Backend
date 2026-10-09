--liquibase formatted sql

--changeset school:035-add-admin-username
ALTER TABLE admin_users ADD COLUMN IF NOT EXISTS username VARCHAR(50);

UPDATE admin_users
SET username = lower(regexp_replace(split_part(email, '@', 1), '[^a-zA-Z0-9._-]', '', 'g'))
WHERE username IS NULL;

UPDATE admin_users
SET username = 'user' || id::text
WHERE username IS NULL OR username = '';

UPDATE admin_users a
SET username = a.username || a.id::text
WHERE a.id IN (
	SELECT id FROM (
		SELECT id, ROW_NUMBER() OVER (PARTITION BY lower(username) ORDER BY id) AS rn
		FROM admin_users
	) ranked
	WHERE rn > 1
);

ALTER TABLE admin_users ALTER COLUMN username SET NOT NULL;
CREATE UNIQUE INDEX IF NOT EXISTS uq_admin_users_username ON admin_users (username);
