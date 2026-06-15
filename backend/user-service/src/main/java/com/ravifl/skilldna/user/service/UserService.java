/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.user.service;

import com.ravifl.skilldna.user.dto.*;
import com.ravifl.skilldna.user.entity.SubscriptionTier;
import com.ravifl.skilldna.user.entity.User;
import com.ravifl.skilldna.user.exception.UserAlreadyExistsException;
import com.ravifl.skilldna.user.exception.UserNotFoundException;
import com.ravifl.skilldna.user.mapper.UserMapper;
import com.ravifl.skilldna.user.repository.UserRepository;
import com.ravifl.skilldna.user.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Core business logic for user registration, authentication, and profile management.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    /**
     * Registers a new user and returns an authentication token.
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
            .email(request.getEmail().toLowerCase().trim())
            .passwordHash(passwordEncoder.encode(request.getPassword()))
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .countryCode(request.getCountryCode())
            .referralCode(generateReferralCode())
            .tier(SubscriptionTier.FREE)
            .build();

        User saved = userRepository.save(user);
        log.info("New user registered: {}", saved.getId());

        return buildAuthResponse(saved);
    }

    /**
     * Authenticates a user and returns a JWT token.
     */
    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail().toLowerCase().trim())
            .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        userRepository.updateLastLogin(user.getId(), LocalDateTime.now());
        log.info("User authenticated: {}", user.getId());

        return buildAuthResponse(user);
    }

    /**
     * Retrieves a user profile by ID.
     */
    public UserResponse getProfile(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        return userMapper.toResponse(user);
    }

    /**
     * Updates basic profile fields for a user.
     */
    @Transactional
    public UserResponse updateProfile(UUID userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getCountryCode() != null) {
            user.setCountryCode(request.getCountryCode());
        }

        User updated = userRepository.save(user);
        log.info("Profile updated for user: {}", userId);
        return userMapper.toResponse(updated);
    }

    /**
     * Upgrades or downgrades a user's subscription tier.
     */
    @Transactional
    public UserResponse updateTier(UUID userId, SubscriptionTier tier) {
        userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.updateTier(userId, tier);
        log.info("Tier updated to {} for user: {}", tier, userId);
        return getProfile(userId);
    }

    // ----------------------------------------------------------------
    // Private helpers
    // ----------------------------------------------------------------

    private AuthResponse buildAuthResponse(User user) {
        String token = jwtService.generateToken(
            user.getId(), user.getEmail(), user.getTier().name());
        UserResponse userResponse = userMapper.toResponse(user);
        return AuthResponse.of(token, jwtService.getExpirationMs() / 1000, userResponse);
    }

    private String generateReferralCode() {
        return "SDNA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
