/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.careertwin.repository;

import com.ravifl.skilldna.careertwin.entity.CareerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for CareerProfile persistence operations.
 */
@Repository
public interface CareerProfileRepository extends JpaRepository<CareerProfile, UUID> {

    Optional<CareerProfile> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    @Query("""
        SELECT cp FROM CareerProfile cp
        LEFT JOIN FETCH cp.skills
        LEFT JOIN FETCH cp.workExperiences
        LEFT JOIN FETCH cp.educations
        LEFT JOIN FETCH cp.certifications
        WHERE cp.userId = :userId
        """)
    Optional<CareerProfile> findByUserIdWithDetails(@Param("userId") UUID userId);
}
