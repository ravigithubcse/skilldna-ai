/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.careertwin.exception;

import java.util.UUID;

/** Thrown when a user already has a career profile. */
public class DuplicateCareerProfileException extends RuntimeException {
    public DuplicateCareerProfileException(UUID userId) {
        super("Career profile already exists for user: " + userId);
    }
}
