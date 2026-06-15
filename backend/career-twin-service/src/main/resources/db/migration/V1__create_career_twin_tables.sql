-- SkillDNA AI — Ravi Future Labs
-- V1: Career Twin tables

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS career_profiles (
    id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id               UUID NOT NULL UNIQUE,
    headline              VARCHAR(300),
    summary               TEXT,
    current_role          VARCHAR(200),
    current_company       VARCHAR(200),
    years_experience      INT,
    current_salary_usd    NUMERIC(12,2),
    target_salary_usd     NUMERIC(12,2),
    automation_risk_score NUMERIC(5,2),
    career_health_score   NUMERIC(5,2),
    profile_completeness  NUMERIC(5,2),
    country_code          VARCHAR(3),
    city                  VARCHAR(100),
    open_to_remote        BOOLEAN NOT NULL DEFAULT FALSE,
    linkedin_url          VARCHAR(500),
    github_url            VARCHAR(500),
    desired_roles         JSONB,
    desired_companies     JSONB,
    last_ai_sync          TIMESTAMP,
    created_at            TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP NOT NULL DEFAULT NOW(),
    version               BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS skill_entries (
    id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    career_profile_id UUID NOT NULL REFERENCES career_profiles(id) ON DELETE CASCADE,
    skill_name        VARCHAR(150) NOT NULL,
    level             VARCHAR(20) NOT NULL,
    category          VARCHAR(30) NOT NULL,
    years_used        INT,
    last_used_date    DATE,
    is_primary        BOOLEAN NOT NULL DEFAULT FALSE,
    demand_score      DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS work_experiences (
    id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    career_profile_id UUID NOT NULL REFERENCES career_profiles(id) ON DELETE CASCADE,
    company_name      VARCHAR(200) NOT NULL,
    role_title        VARCHAR(200) NOT NULL,
    start_date        DATE NOT NULL,
    end_date          DATE,
    is_current        BOOLEAN NOT NULL DEFAULT FALSE,
    description       TEXT,
    salary_usd        NUMERIC(12,2),
    country_code      VARCHAR(3),
    city              VARCHAR(100),
    work_mode         VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS educations (
    id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    career_profile_id UUID NOT NULL REFERENCES career_profiles(id) ON DELETE CASCADE,
    institution_name  VARCHAR(300) NOT NULL,
    degree            VARCHAR(200) NOT NULL,
    field_of_study    VARCHAR(200),
    start_date        DATE,
    end_date          DATE,
    gpa               DOUBLE PRECISION,
    country_code      VARCHAR(3)
);

CREATE TABLE IF NOT EXISTS certifications (
    id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    career_profile_id UUID NOT NULL REFERENCES career_profiles(id) ON DELETE CASCADE,
    cert_name         VARCHAR(300) NOT NULL,
    issuing_org       VARCHAR(200),
    issue_date        DATE,
    expiry_date       DATE,
    credential_id     VARCHAR(200),
    credential_url    VARCHAR(500),
    salary_uplift_pct DOUBLE PRECISION
);

CREATE INDEX idx_cp_user_id      ON career_profiles(user_id);
CREATE INDEX idx_skill_profile   ON skill_entries(career_profile_id);
CREATE INDEX idx_skill_name      ON skill_entries(skill_name);
CREATE INDEX idx_we_profile      ON work_experiences(career_profile_id);
CREATE INDEX idx_edu_profile     ON educations(career_profile_id);
CREATE INDEX idx_cert_profile    ON certifications(career_profile_id);
