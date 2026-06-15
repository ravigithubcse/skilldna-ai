/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.user.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI 3.0 configuration for Swagger UI documentation.
 */
@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name:user-service}")
    private String appName;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("SkillDNA AI — User Service API")
                .description("""
                    User registration, authentication, and profile management service.
                    
                    **Platform**: SkillDNA AI — World's First AI-Powered Career Digital Twin
                    
                    **Company**: Ravi Future Labs | Founder: Ravikumar
                    """)
                .version("v1.0.0")
                .contact(new Contact()
                    .name("Ravikumar — Ravi Future Labs")
                    .url("https://github.com/ravigithubcse/skilldna-ai"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server().url("http://localhost:8081").description("Local Development"),
                new Server().url("https://api.skilldna.ai").description("Production")
            ))
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Enter JWT token obtained from /api/v1/auth/login")));
    }
}
