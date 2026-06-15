/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.careertwin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Root aggregate of the Career Digital Twin — stores all professional data
 * for AI-powered career simulation and prediction.
 */
@Entity
@Table(name = "career_profiles", indexes = {
    @Index(name = "idx_cp_user_id", columnList = "user_id", unique = true),
    @Index(name = "idx_cp_headline", columnList = "headline")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareerProfile {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(length = 300)
    private String headline;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "current_role", length = 200)
    private String currentRole;

    @Column(name = "current_company", length = 200)
    private String currentCompany;

    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Column(name = "current_salary_usd", precision = 12, scale = 2)
    private BigDecimal currentSalaryUsd;

    @Column(name = "target_salary_usd", precision = 12, scale = 2)
    private BigDecimal targetSalaryUsd;

    @Column(name = "automation_risk_score", precision = 5, scale = 2)
    private BigDecimal automationRiskScore;

    @Column(name = "career_health_score", precision = 5, scale = 2)
    private BigDecimal careerHealthScore;

    @Column(name = "country_code", length = 3)
    private String countryCode;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "open_to_remote")
    @Builder.Default
    private boolean openToRemote = false;

    @Column(name = "linkedin_url", length = 500)
    private String linkedinUrl;

    @Column(name = "github_url", length = 500)
    private String githubUrl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "desired_roles", columnDefinition = "jsonb")
    private List<String> desiredRoles;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "desired_companies", columnDefinition = "jsonb")
    private List<String> desiredCompanies;

    @OneToMany(mappedBy = "careerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SkillEntry> skills = new ArrayList<>();

    @OneToMany(mappedBy = "careerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @OneToMany(mappedBy = "careerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "careerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Certification> certifications = new ArrayList<>();

    @Column(name = "last_ai_sync")
    private LocalDateTime lastAiSync;

    @Column(name = "profile_completeness", precision = 5, scale = 2)
    private BigDecimal profileCompleteness;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}
