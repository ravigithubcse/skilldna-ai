/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.careertwin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

/**
 * An education record within a Career Digital Twin.
 */
@Entity
@Table(name = "educations", indexes = {
    @Index(name = "idx_edu_profile_id", columnList = "career_profile_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_profile_id", nullable = false)
    private CareerProfile careerProfile;

    @Column(name = "institution_name", nullable = false, length = 300)
    private String institutionName;

    @Column(nullable = false, length = 200)
    private String degree;

    @Column(name = "field_of_study", length = 200)
    private String fieldOfStudy;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "gpa")
    private Double gpa;

    @Column(name = "country_code", length = 3)
    private String countryCode;
}
