/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.prediction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/** AI prediction result DTO. */
@Data @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
public class PredictionResponse {
    private UUID predictionId;
    private UUID userId;
    private String predictionType;
    private Double confidenceScore;
    private List<CareerPathOption> careerPaths;
    private SalaryBand salaryBand;
    private List<String> recommendedSkills;
    private List<String> insights;
    private LocalDateTime generatedAt;

    @Data @Builder
    public static class CareerPathOption {
        private String roleName;
        private String company;
        private Double fitScore;
        private Double salaryUsd;
        private Integer timelineMonths;
        private List<String> skillGaps;
        private String reasoning;
    }

    @Data @Builder
    public static class SalaryBand {
        private Double lowUsd;
        private Double midUsd;
        private Double highUsd;
        private String currency;
        private String marketPercentile;
    }
}
