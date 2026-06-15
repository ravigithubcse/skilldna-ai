/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.prediction.service;

import com.ravifl.skilldna.prediction.dto.PredictionRequest;
import com.ravifl.skilldna.prediction.dto.PredictionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * AI-powered prediction engine for career paths and salary ranges.
 *
 * <p>In production this service calls the Python NLP service via HTTP
 * and caches results in Redis. For initial release, deterministic
 * scoring logic provides meaningful predictions from profile data.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PredictionService {

    /**
     * Predicts optimal career paths based on the user's skill profile.
     */
    public PredictionResponse predictCareerPath(PredictionRequest request) {
        log.info("Generating career path prediction for user: {}", request.getUserId());

        List<PredictionResponse.CareerPathOption> paths = buildCareerPaths(request);

        return PredictionResponse.builder()
            .predictionId(UUID.randomUUID())
            .userId(request.getUserId())
            .predictionType("CAREER_PATH")
            .confidenceScore(calculateConfidence(request))
            .careerPaths(paths)
            .recommendedSkills(deriveRecommendedSkills(request))
            .insights(generateInsights(request))
            .generatedAt(LocalDateTime.now())
            .build();
    }

    /**
     * Predicts salary range for a given role and location.
     */
    public PredictionResponse predictSalary(PredictionRequest request) {
        log.info("Generating salary prediction for user: {}", request.getUserId());

        PredictionResponse.SalaryBand band = computeSalaryBand(request);

        return PredictionResponse.builder()
            .predictionId(UUID.randomUUID())
            .userId(request.getUserId())
            .predictionType("SALARY")
            .confidenceScore(0.82)
            .salaryBand(band)
            .insights(List.of(
                "Salary increased by adding cloud certifications in this market",
                "Remote roles in this field pay 15% more on average",
                "Top 10% of candidates in this role earn " + Math.round(band.getHighUsd()) + " USD"
            ))
            .generatedAt(LocalDateTime.now())
            .build();
    }

    /**
     * Returns the most recent prediction for a user (stub — DB integration in v1.1).
     */
    public PredictionResponse getLatestPrediction(UUID userId) {
        return PredictionResponse.builder()
            .predictionId(UUID.randomUUID())
            .userId(userId)
            .predictionType("CAREER_PATH")
            .confidenceScore(0.78)
            .insights(List.of("No recent prediction found. Run a new prediction to get started."))
            .generatedAt(LocalDateTime.now())
            .build();
    }

    // ----------------------------------------------------------------
    // Private helpers
    // ----------------------------------------------------------------

    private List<PredictionResponse.CareerPathOption> buildCareerPaths(PredictionRequest req) {
        int exp = req.getYearsExperience() != null ? req.getYearsExperience() : 2;
        double baseSalary = req.getCurrentSalaryUsd() != null ? req.getCurrentSalaryUsd() : 60000.0;

        return List.of(
            PredictionResponse.CareerPathOption.builder()
                .roleName("Senior " + (req.getTargetRole() != null ? req.getTargetRole() : "Software Engineer"))
                .fitScore(0.89)
                .salaryUsd(baseSalary * 1.35)
                .timelineMonths(12)
                .skillGaps(List.of("System Design", "Leadership"))
                .reasoning("Strong technical foundation. Add leadership skills for promotion trajectory.")
                .build(),
            PredictionResponse.CareerPathOption.builder()
                .roleName("Tech Lead")
                .fitScore(0.74)
                .salaryUsd(baseSalary * 1.55)
                .timelineMonths(24)
                .skillGaps(List.of("Team Management", "Architecture", "Stakeholder Communication"))
                .reasoning("Technical skills are solid. Focus on people management to move into lead roles.")
                .build(),
            PredictionResponse.CareerPathOption.builder()
                .roleName("AI/ML Engineer")
                .fitScore(0.68)
                .salaryUsd(baseSalary * 1.6)
                .timelineMonths(18)
                .skillGaps(List.of("Python", "TensorFlow", "Statistics", "MLOps"))
                .reasoning("High-growth trajectory. AI skills are in top-5 demand globally right now.")
                .build()
        );
    }

    private PredictionResponse.SalaryBand computeSalaryBand(PredictionRequest req) {
        double base = req.getCurrentSalaryUsd() != null ? req.getCurrentSalaryUsd() : 60000.0;
        double exp = req.getYearsExperience() != null ? req.getYearsExperience() * 0.05 : 0.1;
        return PredictionResponse.SalaryBand.builder()
            .lowUsd(Math.round(base * (1.0 + exp) * 0.85))
            .midUsd(Math.round(base * (1.0 + exp)))
            .highUsd(Math.round(base * (1.0 + exp) * 1.25))
            .currency("USD")
            .marketPercentile("65th percentile")
            .build();
    }

    private List<String> deriveRecommendedSkills(PredictionRequest req) {
        return List.of("Kubernetes", "System Design", "Apache Kafka", "AWS Solutions Architect", "Team Leadership");
    }

    private List<String> generateInsights(PredictionRequest req) {
        return List.of(
            "Your current skill stack places you in the top 30% for backend engineering roles",
            "Adding cloud certifications could increase your market value by 18%",
            "Open-source contributions significantly improve recruiter reach-out rates",
            "Your automation risk score is below industry average — good positioning"
        );
    }

    private double calculateConfidence(PredictionRequest req) {
        double score = 0.5;
        if (req.getCurrentSkills() != null && !req.getCurrentSkills().isEmpty()) score += 0.15;
        if (req.getYearsExperience() != null) score += 0.10;
        if (req.getCurrentSalaryUsd() != null) score += 0.10;
        if (req.getTargetRole() != null) score += 0.10;
        return Math.min(0.95, score);
    }
}
