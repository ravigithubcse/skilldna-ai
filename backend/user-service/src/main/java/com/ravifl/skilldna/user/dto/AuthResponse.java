/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.user.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Response DTO returned after successful authentication.
 */
@Data
@Builder
public class AuthResponse {

    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private UserResponse user;

    public static AuthResponse of(String token, long expiresIn, UserResponse user) {
        return AuthResponse.builder()
            .accessToken(token)
            .tokenType("Bearer")
            .expiresIn(expiresIn)
            .user(user)
            .build();
    }
}
