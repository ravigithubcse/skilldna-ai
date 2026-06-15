/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.jobmarket.controller;

import com.ravifl.skilldna.jobmarket.service.JobMarketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

/** Real-time job market intelligence REST API. */
@RestController
@RequestMapping("/api/v1/job-markets")
@RequiredArgsConstructor
@Tag(name = "Job Market", description = "Real-time job market intelligence")
public class JobMarketController {

    private final JobMarketService service;

    @GetMapping("/health")
    @Operation(summary = "Service health check")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "service", "job-market-service",
            "status", "UP",
            "version", "1.0.0"
        ));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get job-market data for user")
    public ResponseEntity<Map<String, Object>> getForUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getForUser(userId));
    }

    @PostMapping("/user/{userId}/generate")
    @Operation(summary = "Generate AI-powered job-market recommendations for user")
    public ResponseEntity<Map<String, Object>> generate(
            @PathVariable UUID userId,
            @RequestBody(required = false) Map<String, Object> params) {
        return ResponseEntity.ok(service.generate(userId, params));
    }
}
