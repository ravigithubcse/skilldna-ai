/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.user.dto;

import com.ravifl.skilldna.user.entity.SubscriptionTier;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Public-facing representation of a user profile.
 */
@Data
@Builder
public class UserResponse {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private SubscriptionTier tier;
    private String countryCode;
    private String referralCode;
    private boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}
