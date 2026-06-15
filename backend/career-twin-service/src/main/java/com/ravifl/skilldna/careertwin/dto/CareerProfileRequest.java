/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.careertwin.dto;

import com.ravifl.skilldna.careertwin.entity.SkillCategory;
import com.ravifl.skilldna.careertwin.entity.SkillLevel;
import com.ravifl.skilldna.careertwin.entity.WorkMode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Request DTO for creating or fully updating a Career Digital Twin profile.
 */
@Data
public class CareerProfileRequest {

    @Size(max = 300)
    private String headline;

    @Size(max = 2000)
    private String summary;

    @Size(max = 200)
    private String currentRole;

    @Size(max = 200)
    private String currentCompany;

    @Min(0) @Max(60)
    private Integer yearsExperience;

    @DecimalMin("0.00")
    private BigDecimal currentSalaryUsd;

    @DecimalMin("0.00")
    private BigDecimal targetSalaryUsd;

    @Size(max = 3)
    private String countryCode;

    @Size(max = 100)
    private String city;

    private boolean openToRemote;

    @Size(max = 500)
    private String linkedinUrl;

    @Size(max = 500)
    private String githubUrl;

    private List<String> desiredRoles;
    private List<String> desiredCompanies;

    @Valid
    private List<SkillRequest> skills;

    @Valid
    private List<WorkExperienceRequest> workExperiences;

    @Valid
    private List<EducationRequest> educations;

    @Valid
    private List<CertificationRequest> certifications;

    // ----------------------------------------------------------------
    // Nested request types
    // ----------------------------------------------------------------

    @Data
    public static class SkillRequest {
        @NotBlank @Size(max = 150)
        private String skillName;
        @NotNull
        private SkillLevel level;
        @NotNull
        private SkillCategory category;
        @Min(0) @Max(50)
        private Integer yearsUsed;
        private LocalDate lastUsedDate;
        private boolean isPrimary;
    }

    @Data
    public static class WorkExperienceRequest {
        @NotBlank @Size(max = 200)
        private String companyName;
        @NotBlank @Size(max = 200)
        private String roleTitle;
        @NotNull
        private LocalDate startDate;
        private LocalDate endDate;
        private boolean isCurrent;
        @Size(max = 2000)
        private String description;
        @DecimalMin("0.00")
        private BigDecimal salaryUsd;
        @Size(max = 3)
        private String countryCode;
        @Size(max = 100)
        private String city;
        private WorkMode workMode;
    }

    @Data
    public static class EducationRequest {
        @NotBlank @Size(max = 300)
        private String institutionName;
        @NotBlank @Size(max = 200)
        private String degree;
        @Size(max = 200)
        private String fieldOfStudy;
        private LocalDate startDate;
        private LocalDate endDate;
        @DecimalMin("0.0") @DecimalMax("10.0")
        private Double gpa;
        @Size(max = 3)
        private String countryCode;
    }

    @Data
    public static class CertificationRequest {
        @NotBlank @Size(max = 300)
        private String certName;
        @Size(max = 200)
        private String issuingOrg;
        private LocalDate issueDate;
        private LocalDate expiryDate;
        @Size(max = 200)
        private String credentialId;
        @Size(max = 500)
        private String credentialUrl;
    }
}
