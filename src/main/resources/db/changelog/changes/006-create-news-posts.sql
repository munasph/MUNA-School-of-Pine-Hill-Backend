--liquibase formatted sql

--changeset school:006-create-news-posts
CREATE TABLE news_posts (
    id           BIGSERIAL PRIMARY KEY,
    slug         VARCHAR(200) NOT NULL,
    title        VARCHAR(300) NOT NULL,
    summary      VARCHAR(500),
    body         TEXT,
    image_url    VARCHAR(500),
    author       VARCHAR(200),
    status       VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    published_at TIMESTAMPTZ,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_news_posts_slug UNIQUE (slug)
);
