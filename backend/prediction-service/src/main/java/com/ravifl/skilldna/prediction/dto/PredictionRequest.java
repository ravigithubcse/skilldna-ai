/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.prediction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;
import java.util.UUID;

/** Request DTO for career path and salary prediction. */
@Data
public class PredictionRequest {
    @NotNull private UUID userId;
    private String targetRole;
    private String targetCountry;
    private String targetCity;
    private Integer timeHorizonMonths;
    private List<String> currentSkills;
    private Integer yearsExperience;
    private Double currentSalaryUsd;
}
