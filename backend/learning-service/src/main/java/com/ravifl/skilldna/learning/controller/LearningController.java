/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.learning.controller;

import com.ravifl.skilldna.learning.service.LearningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

/** Personalised skill gap and learning path engine REST API. */
@RestController
@RequestMapping("/api/v1/learnings")
@RequiredArgsConstructor
@Tag(name = "Learning", description = "Personalised skill gap and learning path engine")
public class LearningController {

    private final LearningService service;

    @GetMapping("/health")
    @Operation(summary = "Service health check")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "service", "learning-service",
            "status", "UP",
            "version", "1.0.0"
        ));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get learning data for user")
    public ResponseEntity<Map<String, Object>> getForUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getForUser(userId));
    }

    @PostMapping("/user/{userId}/generate")
    @Operation(summary = "Generate AI-powered learning recommendations for user")
    public ResponseEntity<Map<String, Object>> generate(
            @PathVariable UUID userId,
            @RequestBody(required = false) Map<String, Object> params) {
        return ResponseEntity.ok(service.generate(userId, params));
    }
}
