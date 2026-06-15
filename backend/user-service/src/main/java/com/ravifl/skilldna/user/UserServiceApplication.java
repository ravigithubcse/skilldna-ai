/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * SkillDNA AI — User Service
 *
 * <p>Handles user registration, JWT-based authentication, profile management,
 * and subscription tier lifecycle events.</p>
 *
 * @author Ravikumar — Ravi Future Labs
 * @version 1.0.0
 * @since 2026
 */
@SpringBootApplication
@EnableKafka
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
