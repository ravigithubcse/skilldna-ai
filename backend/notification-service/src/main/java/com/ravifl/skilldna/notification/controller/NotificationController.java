/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.notification.controller;

import com.ravifl.skilldna.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

/** Multi-channel notification delivery REST API. */
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Multi-channel notification delivery")
public class NotificationController {

    private final NotificationService service;

    @GetMapping("/health")
    @Operation(summary = "Service health check")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "service", "notification-service",
            "status", "UP",
            "version", "1.0.0"
        ));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get notification data for user")
    public ResponseEntity<Map<String, Object>> getForUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getForUser(userId));
    }

    @PostMapping("/user/{userId}/generate")
    @Operation(summary = "Generate AI-powered notification recommendations for user")
    public ResponseEntity<Map<String, Object>> generate(
            @PathVariable UUID userId,
            @RequestBody(required = false) Map<String, Object> params) {
        return ResponseEntity.ok(service.generate(userId, params));
    }
}
