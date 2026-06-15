/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.prediction.controller;

import com.ravifl.skilldna.prediction.dto.ApiResponse;
import com.ravifl.skilldna.prediction.dto.PredictionRequest;
import com.ravifl.skilldna.prediction.dto.PredictionResponse;
import com.ravifl.skilldna.prediction.service.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST API for AI-powered career path and salary predictions.
 */
@RestController
@RequestMapping("/api/v1/predictions")
@RequiredArgsConstructor
@Tag(name = "Predictions", description = "AI career path and salary prediction engine")
@SecurityRequirement(name = "bearerAuth")
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping("/career-path")
    @Operation(summary = "Predict optimal career paths", description = "Returns AI-scored career trajectory options based on the user's digital twin")
    public ResponseEntity<ApiResponse<PredictionResponse>> predictCareerPath(
            @Valid @RequestBody PredictionRequest request) {
        PredictionResponse response = predictionService.predictCareerPath(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/salary")
    @Operation(summary = "Predict salary range", description = "Returns AI-predicted salary band for a target role and location")
    public ResponseEntity<ApiResponse<PredictionResponse>> predictSalary(
            @Valid @RequestBody PredictionRequest request) {
        PredictionResponse response = predictionService.predictSalary(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/user/{userId}/latest")
    @Operation(summary = "Get latest predictions for a user")
    public ResponseEntity<ApiResponse<PredictionResponse>> getLatestPrediction(
            @PathVariable UUID userId) {
        PredictionResponse response = predictionService.getLatestPrediction(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
