/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SkillDNA AI — API Gateway
 *
 * <p>Reactive Spring Cloud Gateway handling JWT validation, CORS,
 * rate limiting, and routing to downstream microservices.</p>
 *
 * @author Ravikumar — Ravi Future Labs
 * @version 1.0.0
 */
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
