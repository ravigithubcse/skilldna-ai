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
 * A single skill entry within a Career Digital Twin.
 */
@Entity
@Table(name = "skill_entries", indexes = {
    @Index(name = "idx_skill_profile_id", columnList = "career_profile_id"),
    @Index(name = "idx_skill_name", columnList = "skill_name"),
    @Index(name = "idx_skill_category", columnList = "category")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillEntry {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_profile_id", nullable = false)
    private CareerProfile careerProfile;

    @Column(name = "skill_name", nullable = false, length = 150)
    private String skillName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SkillLevel level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SkillCategory category;

    @Column(name = "years_used")
    private Integer yearsUsed;

    @Column(name = "last_used_date")
    private LocalDate lastUsedDate;

    @Column(name = "is_primary")
    @Builder.Default
    private boolean isPrimary = false;

    @Column(name = "demand_score")
    private Double demandScore;
}
