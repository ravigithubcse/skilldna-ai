/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.careertwin.controller;

import com.ravifl.skilldna.careertwin.dto.ApiResponse;
import com.ravifl.skilldna.careertwin.dto.CareerProfileRequest;
import com.ravifl.skilldna.careertwin.dto.CareerProfileResponse;
import com.ravifl.skilldna.careertwin.service.CareerTwinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST API for Career Digital Twin — create, read, update, delete career profiles.
 */
@RestController
@RequestMapping("/api/v1/career-twin")
@RequiredArgsConstructor
@Tag(name = "Career Twin", description = "Career Digital Twin profile management")
@SecurityRequirement(name = "bearerAuth")
public class CareerTwinController {

    private final CareerTwinService careerTwinService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Career Digital Twin", description = "Initialises a new AI-powered career twin for the authenticated user")
    public ResponseEntity<ApiResponse<CareerProfileResponse>> createProfile(
            @AuthenticationPrincipal String userId,
            @Valid @RequestBody CareerProfileRequest request) {
        CareerProfileResponse response = careerTwinService.createProfile(UUID.fromString(userId), request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Career Twin created successfully", response));
    }

    @GetMapping
    @Operation(summary = "Get my Career Digital Twin")
    public ResponseEntity<ApiResponse<CareerProfileResponse>> getMyProfile(
            @AuthenticationPrincipal String userId) {
        CareerProfileResponse response = careerTwinService.getProfile(UUID.fromString(userId));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping
    @Operation(summary = "Update Career Digital Twin", description = "Fully replaces the career twin profile and recalculates AI scores")
    public ResponseEntity<ApiResponse<CareerProfileResponse>> updateProfile(
            @AuthenticationPrincipal String userId,
            @Valid @RequestBody CareerProfileRequest request) {
        CareerProfileResponse response = careerTwinService.updateProfile(UUID.fromString(userId), request);
        return ResponseEntity.ok(ApiResponse.success("Career Twin updated", response));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Career Digital Twin")
    public ResponseEntity<Void> deleteProfile(@AuthenticationPrincipal String userId) {
        careerTwinService.deleteProfile(UUID.fromString(userId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/admin")
    @Operation(summary = "Get career profile by userId (admin)")
    public ResponseEntity<ApiResponse<CareerProfileResponse>> getProfileAdmin(
            @PathVariable UUID userId) {
        CareerProfileResponse response = careerTwinService.getProfile(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
