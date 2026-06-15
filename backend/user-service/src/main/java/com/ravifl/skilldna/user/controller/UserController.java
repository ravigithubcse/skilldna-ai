/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.user.controller;

import com.ravifl.skilldna.user.dto.ApiResponse;
import com.ravifl.skilldna.user.dto.UpdateProfileRequest;
import com.ravifl.skilldna.user.dto.UserResponse;
import com.ravifl.skilldna.user.entity.SubscriptionTier;
import com.ravifl.skilldna.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST endpoints for user profile management.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User profile operations")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile(
            @AuthenticationPrincipal String userId) {
        UserResponse response = userService.getProfile(UUID.fromString(userId));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/me")
    @Operation(summary = "Update current user profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateMyProfile(
            @AuthenticationPrincipal String userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        UserResponse response = userService.updateProfile(UUID.fromString(userId), request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated", response));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID (admin)")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable UUID userId) {
        UserResponse response = userService.getProfile(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{userId}/tier")
    @Operation(summary = "Update subscription tier (admin/webhook)")
    public ResponseEntity<ApiResponse<UserResponse>> updateTier(
            @PathVariable UUID userId,
            @RequestParam SubscriptionTier tier) {
        UserResponse response = userService.updateTier(userId, tier);
        return ResponseEntity.ok(ApiResponse.success("Tier updated", response));
    }
}
