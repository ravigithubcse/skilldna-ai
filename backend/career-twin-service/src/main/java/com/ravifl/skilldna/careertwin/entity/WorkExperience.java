/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.careertwin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A work experience record in a Career Digital Twin.
 */
@Entity
@Table(name = "work_experiences", indexes = {
    @Index(name = "idx_we_profile_id", columnList = "career_profile_id"),
    @Index(name = "idx_we_company", columnList = "company_name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperience {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_profile_id", nullable = false)
    private CareerProfile careerProfile;

    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(name = "role_title", nullable = false, length = 200)
    private String roleTitle;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_current")
    @Builder.Default
    private boolean isCurrent = false;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "salary_usd", precision = 12, scale = 2)
    private BigDecimal salaryUsd;

    @Column(name = "country_code", length = 3)
    private String countryCode;

    @Column(length = 100)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_mode", length = 20)
    private WorkMode workMode;
}
