/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.careertwin.service;

import com.ravifl.skilldna.careertwin.dto.CareerProfileRequest;
import com.ravifl.skilldna.careertwin.dto.CareerProfileResponse;
import com.ravifl.skilldna.careertwin.entity.*;
import com.ravifl.skilldna.careertwin.exception.CareerProfileNotFoundException;
import com.ravifl.skilldna.careertwin.exception.DuplicateCareerProfileException;
import com.ravifl.skilldna.careertwin.repository.CareerProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Core business logic for Career Digital Twin creation, retrieval, and AI scoring.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerTwinService {

    private final CareerProfileRepository repository;

    /**
     * Creates a new Career Digital Twin for a user.
     */
    @Transactional
    public CareerProfileResponse createProfile(UUID userId, CareerProfileRequest request) {
        if (repository.existsByUserId(userId)) {
            throw new DuplicateCareerProfileException(userId);
        }

        CareerProfile profile = buildProfile(userId, request);
        profile.setProfileCompleteness(calculateCompleteness(profile));
        profile.setAutomationRiskScore(computeAutomationRisk(profile));
        profile.setCareerHealthScore(computeCareerHealth(profile));

        CareerProfile saved = repository.save(profile);
        log.info("Career Twin created for user: {}", userId);
        return toResponse(saved);
    }

    /**
     * Retrieves a Career Digital Twin with all details.
     */
    public CareerProfileResponse getProfile(UUID userId) {
        CareerProfile profile = repository.findByUserIdWithDetails(userId)
            .orElseThrow(() -> new CareerProfileNotFoundException(userId));
        return toResponse(profile);
    }

    /**
     * Fully replaces a Career Digital Twin.
     */
    @Transactional
    public CareerProfileResponse updateProfile(UUID userId, CareerProfileRequest request) {
        CareerProfile existing = repository.findByUserId(userId)
            .orElseThrow(() -> new CareerProfileNotFoundException(userId));

        applyUpdates(existing, request);
        existing.setProfileCompleteness(calculateCompleteness(existing));
        existing.setAutomationRiskScore(computeAutomationRisk(existing));
        existing.setCareerHealthScore(computeCareerHealth(existing));
        existing.setLastAiSync(LocalDateTime.now());

        CareerProfile saved = repository.save(existing);
        log.info("Career Twin updated for user: {}", userId);
        return toResponse(saved);
    }

    /**
     * Deletes a Career Digital Twin.
     */
    @Transactional
    public void deleteProfile(UUID userId) {
        CareerProfile profile = repository.findByUserId(userId)
            .orElseThrow(() -> new CareerProfileNotFoundException(userId));
        repository.delete(profile);
        log.info("Career Twin deleted for user: {}", userId);
    }

    // ----------------------------------------------------------------
    // Private helpers — builders & mappers
    // ----------------------------------------------------------------

    private CareerProfile buildProfile(UUID userId, CareerProfileRequest req) {
        CareerProfile profile = CareerProfile.builder()
            .userId(userId)
            .headline(req.getHeadline())
            .summary(req.getSummary())
            .currentRole(req.getCurrentRole())
            .currentCompany(req.getCurrentCompany())
            .yearsExperience(req.getYearsExperience())
            .currentSalaryUsd(req.getCurrentSalaryUsd())
            .targetSalaryUsd(req.getTargetSalaryUsd())
            .countryCode(req.getCountryCode())
            .city(req.getCity())
            .openToRemote(req.isOpenToRemote())
            .linkedinUrl(req.getLinkedinUrl())
            .githubUrl(req.getGithubUrl())
            .desiredRoles(req.getDesiredRoles())
            .desiredCompanies(req.getDesiredCompanies())
            .build();

        if (req.getSkills() != null) {
            List<SkillEntry> skills = req.getSkills().stream()
                .map(s -> buildSkill(s, profile))
                .collect(Collectors.toList());
            profile.getSkills().addAll(skills);
        }

        if (req.getWorkExperiences() != null) {
            List<WorkExperience> exps = req.getWorkExperiences().stream()
                .map(w -> buildWorkExp(w, profile))
                .collect(Collectors.toList());
            profile.getWorkExperiences().addAll(exps);
        }

        if (req.getEducations() != null) {
            List<Education> edus = req.getEducations().stream()
                .map(e -> buildEducation(e, profile))
                .collect(Collectors.toList());
            profile.getEducations().addAll(edus);
        }

        if (req.getCertifications() != null) {
            List<Certification> certs = req.getCertifications().stream()
                .map(c -> buildCertification(c, profile))
                .collect(Collectors.toList());
            profile.getCertifications().addAll(certs);
        }

        return profile;
    }

    private void applyUpdates(CareerProfile profile, CareerProfileRequest req) {
        profile.setHeadline(req.getHeadline());
        profile.setSummary(req.getSummary());
        profile.setCurrentRole(req.getCurrentRole());
        profile.setCurrentCompany(req.getCurrentCompany());
        profile.setYearsExperience(req.getYearsExperience());
        profile.setCurrentSalaryUsd(req.getCurrentSalaryUsd());
        profile.setTargetSalaryUsd(req.getTargetSalaryUsd());
        profile.setCountryCode(req.getCountryCode());
        profile.setCity(req.getCity());
        profile.setOpenToRemote(req.isOpenToRemote());
        profile.setLinkedinUrl(req.getLinkedinUrl());
        profile.setGithubUrl(req.getGithubUrl());
        profile.setDesiredRoles(req.getDesiredRoles());
        profile.setDesiredCompanies(req.getDesiredCompanies());

        profile.getSkills().clear();
        profile.getWorkExperiences().clear();
        profile.getEducations().clear();
        profile.getCertifications().clear();

        if (req.getSkills() != null) {
            req.getSkills().stream().map(s -> buildSkill(s, profile)).forEach(profile.getSkills()::add);
        }
        if (req.getWorkExperiences() != null) {
            req.getWorkExperiences().stream().map(w -> buildWorkExp(w, profile)).forEach(profile.getWorkExperiences()::add);
        }
        if (req.getEducations() != null) {
            req.getEducations().stream().map(e -> buildEducation(e, profile)).forEach(profile.getEducations()::add);
        }
        if (req.getCertifications() != null) {
            req.getCertifications().stream().map(c -> buildCertification(c, profile)).forEach(profile.getCertifications()::add);
        }
    }

    private SkillEntry buildSkill(CareerProfileRequest.SkillRequest req, CareerProfile profile) {
        return SkillEntry.builder()
            .careerProfile(profile)
            .skillName(req.getSkillName())
            .level(req.getLevel())
            .category(req.getCategory())
            .yearsUsed(req.getYearsUsed())
            .lastUsedDate(req.getLastUsedDate())
            .isPrimary(req.isPrimary())
            .build();
    }

    private WorkExperience buildWorkExp(CareerProfileRequest.WorkExperienceRequest req, CareerProfile profile) {
        return WorkExperience.builder()
            .careerProfile(profile)
            .companyName(req.getCompanyName())
            .roleTitle(req.getRoleTitle())
            .startDate(req.getStartDate())
            .endDate(req.getEndDate())
            .isCurrent(req.isCurrent())
            .description(req.getDescription())
            .salaryUsd(req.getSalaryUsd())
            .countryCode(req.getCountryCode())
            .city(req.getCity())
            .workMode(req.getWorkMode())
            .build();
    }

    private Education buildEducation(CareerProfileRequest.EducationRequest req, CareerProfile profile) {
        return Education.builder()
            .careerProfile(profile)
            .institutionName(req.getInstitutionName())
            .degree(req.getDegree())
            .fieldOfStudy(req.getFieldOfStudy())
            .startDate(req.getStartDate())
            .endDate(req.getEndDate())
            .gpa(req.getGpa())
            .countryCode(req.getCountryCode())
            .build();
    }

    private Certification buildCertification(CareerProfileRequest.CertificationRequest req, CareerProfile profile) {
        return Certification.builder()
            .careerProfile(profile)
            .certName(req.getCertName())
            .issuingOrg(req.getIssuingOrg())
            .issueDate(req.getIssueDate())
            .expiryDate(req.getExpiryDate())
            .credentialId(req.getCredentialId())
            .credentialUrl(req.getCredentialUrl())
            .build();
    }

    /**
     * Calculates profile completeness (0–100%) based on populated fields.
     */
    private BigDecimal calculateCompleteness(CareerProfile profile) {
        int score = 0;
        if (profile.getHeadline() != null) score += 10;
        if (profile.getSummary() != null) score += 10;
        if (profile.getCurrentRole() != null) score += 10;
        if (profile.getCurrentSalaryUsd() != null) score += 10;
        if (!profile.getSkills().isEmpty()) score += 20;
        if (!profile.getWorkExperiences().isEmpty()) score += 20;
        if (!profile.getEducations().isEmpty()) score += 10;
        if (!profile.getCertifications().isEmpty()) score += 10;
        return BigDecimal.valueOf(score).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Computes automation risk score (0–100) based on skill categories.
     * Higher score = higher automation risk.
     */
    private BigDecimal computeAutomationRisk(CareerProfile profile) {
        if (profile.getSkills().isEmpty()) {
            return BigDecimal.valueOf(50.0);
        }
        long highRiskCount = profile.getSkills().stream()
            .filter(s -> s.getCategory() == SkillCategory.DOMAIN || s.getCategory() == SkillCategory.OTHER)
            .count();
        double riskRatio = (double) highRiskCount / profile.getSkills().size();
        double score = Math.min(85.0, riskRatio * 100 + 15.0);
        return BigDecimal.valueOf(score).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Computes career health score (0–100).
     * Higher score = better career position.
     */
    private BigDecimal computeCareerHealth(CareerProfile profile) {
        double score = 40.0;
        if (profile.getYearsExperience() != null) score += Math.min(20.0, profile.getYearsExperience() * 2.0);
        if (!profile.getCertifications().isEmpty()) score += Math.min(15.0, profile.getCertifications().size() * 5.0);
        long advancedSkills = profile.getSkills().stream()
            .filter(s -> s.getLevel() == SkillLevel.ADVANCED || s.getLevel() == SkillLevel.EXPERT)
            .count();
        score += Math.min(25.0, advancedSkills * 5.0);
        return BigDecimal.valueOf(Math.min(100.0, score)).setScale(2, RoundingMode.HALF_UP);
    }

    private CareerProfileResponse toResponse(CareerProfile p) {
        return CareerProfileResponse.builder()
            .id(p.getId())
            .userId(p.getUserId())
            .headline(p.getHeadline())
            .summary(p.getSummary())
            .currentRole(p.getCurrentRole())
            .currentCompany(p.getCurrentCompany())
            .yearsExperience(p.getYearsExperience())
            .currentSalaryUsd(p.getCurrentSalaryUsd())
            .targetSalaryUsd(p.getTargetSalaryUsd())
            .automationRiskScore(p.getAutomationRiskScore())
            .careerHealthScore(p.getCareerHealthScore())
            .profileCompleteness(p.getProfileCompleteness())
            .countryCode(p.getCountryCode())
            .city(p.getCity())
            .openToRemote(p.isOpenToRemote())
            .linkedinUrl(p.getLinkedinUrl())
            .githubUrl(p.getGithubUrl())
            .desiredRoles(p.getDesiredRoles())
            .desiredCompanies(p.getDesiredCompanies())
            .skills(p.getSkills().stream().map(this::toSkillResponse).collect(Collectors.toList()))
            .workExperiences(p.getWorkExperiences().stream().map(this::toWorkExpResponse).collect(Collectors.toList()))
            .educations(p.getEducations().stream().map(this::toEduResponse).collect(Collectors.toList()))
            .certifications(p.getCertifications().stream().map(this::toCertResponse).collect(Collectors.toList()))
            .lastAiSync(p.getLastAiSync())
            .createdAt(p.getCreatedAt())
            .updatedAt(p.getUpdatedAt())
            .build();
    }

    private CareerProfileResponse.SkillResponse toSkillResponse(SkillEntry s) {
        return CareerProfileResponse.SkillResponse.builder()
            .id(s.getId()).skillName(s.getSkillName()).level(s.getLevel())
            .category(s.getCategory()).yearsUsed(s.getYearsUsed())
            .lastUsedDate(s.getLastUsedDate()).isPrimary(s.isPrimary())
            .demandScore(s.getDemandScore()).build();
    }

    private CareerProfileResponse.WorkExperienceResponse toWorkExpResponse(WorkExperience w) {
        return CareerProfileResponse.WorkExperienceResponse.builder()
            .id(w.getId()).companyName(w.getCompanyName()).roleTitle(w.getRoleTitle())
            .startDate(w.getStartDate()).endDate(w.getEndDate()).isCurrent(w.isCurrent())
            .description(w.getDescription()).salaryUsd(w.getSalaryUsd())
            .countryCode(w.getCountryCode()).city(w.getCity()).workMode(w.getWorkMode()).build();
    }

    private CareerProfileResponse.EducationResponse toEduResponse(Education e) {
        return CareerProfileResponse.EducationResponse.builder()
            .id(e.getId()).institutionName(e.getInstitutionName()).degree(e.getDegree())
            .fieldOfStudy(e.getFieldOfStudy()).startDate(e.getStartDate())
            .endDate(e.getEndDate()).gpa(e.getGpa()).countryCode(e.getCountryCode()).build();
    }

    private CareerProfileResponse.CertificationResponse toCertResponse(Certification c) {
        return CareerProfileResponse.CertificationResponse.builder()
            .id(c.getId()).certName(c.getCertName()).issuingOrg(c.getIssuingOrg())
            .issueDate(c.getIssueDate()).expiryDate(c.getExpiryDate())
            .credentialId(c.getCredentialId()).credentialUrl(c.getCredentialUrl())
            .salaryUpliftPct(c.getSalaryUpliftPct()).build();
    }
}
