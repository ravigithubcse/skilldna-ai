/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.careertwin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ravifl.skilldna.careertwin.entity.SkillCategory;
import com.ravifl.skilldna.careertwin.entity.SkillLevel;
import com.ravifl.skilldna.careertwin.entity.WorkMode;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Full Career Digital Twin profile response.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CareerProfileResponse {

    private UUID id;
    private UUID userId;
    private String headline;
    private String summary;
    private String currentRole;
    private String currentCompany;
    private Integer yearsExperience;
    private BigDecimal currentSalaryUsd;
    private BigDecimal targetSalaryUsd;
    private BigDecimal automationRiskScore;
    private BigDecimal careerHealthScore;
    private BigDecimal profileCompleteness;
    private String countryCode;
    private String city;
    private boolean openToRemote;
    private String linkedinUrl;
    private String githubUrl;
    private List<String> desiredRoles;
    private List<String> desiredCompanies;
    private List<SkillResponse> skills;
    private List<WorkExperienceResponse> workExperiences;
    private List<EducationResponse> educations;
    private List<CertificationResponse> certifications;
    private LocalDateTime lastAiSync;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data @Builder
    public static class SkillResponse {
        private UUID id;
        private String skillName;
        private SkillLevel level;
        private SkillCategory category;
        private Integer yearsUsed;
        private LocalDate lastUsedDate;
        private boolean isPrimary;
        private Double demandScore;
    }

    @Data @Builder
    public static class WorkExperienceResponse {
        private UUID id;
        private String companyName;
        private String roleTitle;
        private LocalDate startDate;
        private LocalDate endDate;
        private boolean isCurrent;
        private String description;
        private BigDecimal salaryUsd;
        private String countryCode;
        private String city;
        private WorkMode workMode;
    }

    @Data @Builder
    public static class EducationResponse {
        private UUID id;
        private String institutionName;
        private String degree;
        private String fieldOfStudy;
        private LocalDate startDate;
        private LocalDate endDate;
        private Double gpa;
        private String countryCode;
    }

    @Data @Builder
    public static class CertificationResponse {
        private UUID id;
        private String certName;
        private String issuingOrg;
        private LocalDate issueDate;
        private LocalDate expiryDate;
        private String credentialId;
        private String credentialUrl;
        private Double salaryUpliftPct;
    }
}
