/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request DTO for updating user profile.
 */
@Data
public class UpdateProfileRequest {

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 3)
    private String countryCode;
}
