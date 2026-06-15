/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.careertwin.exception;

import java.util.UUID;

/** Thrown when a career profile is not found for a user. */
public class CareerProfileNotFoundException extends RuntimeException {
    public CareerProfileNotFoundException(UUID userId) {
        super("Career profile not found for user: " + userId);
    }
}
