/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.user.repository;

import com.ravifl.skilldna.user.entity.SubscriptionTier;
import com.ravifl.skilldna.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for User entity persistence operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByReferralCode(String referralCode);

    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.lastLogin = :loginTime WHERE u.id = :userId")
    void updateLastLogin(@Param("userId") UUID userId, @Param("loginTime") LocalDateTime loginTime);

    @Modifying
    @Query("UPDATE User u SET u.tier = :tier WHERE u.id = :userId")
    void updateTier(@Param("userId") UUID userId, @Param("tier") SubscriptionTier tier);

    @Query("SELECT COUNT(u) FROM User u WHERE u.tier = :tier")
    long countByTier(@Param("tier") SubscriptionTier tier);
}
