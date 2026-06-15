-- SkillDNA AI — Ravi Future Labs
-- V1: Create users table

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS users (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email           VARCHAR(255) NOT NULL UNIQUE,
    password_hash   TEXT NOT NULL,
    first_name      VARCHAR(100),
    last_name       VARCHAR(100),
    tier            VARCHAR(20) NOT NULL DEFAULT 'FREE',
    country_code    VARCHAR(3),
    referral_code   VARCHAR(20) UNIQUE,
    email_verified  BOOLEAN NOT NULL DEFAULT FALSE,
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,
    last_login      TIMESTAMP,
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    version         BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_users_email   ON users(email);
CREATE INDEX idx_users_tier    ON users(tier);
CREATE INDEX idx_users_referral ON users(referral_code);

COMMENT ON TABLE  users IS 'Registered SkillDNA AI users';
COMMENT ON COLUMN users.tier IS 'FREE | PRO | EXPERT | ENTERPRISE';
