/*
 * SkillDNA AI — Ravi Future Labs
 * Copyright (c) 2026 Ravikumar. All rights reserved.
 */
package com.ravifl.skilldna.user;

import com.ravifl.skilldna.user.dto.*;
import com.ravifl.skilldna.user.entity.SubscriptionTier;
import com.ravifl.skilldna.user.entity.User;
import com.ravifl.skilldna.user.exception.UserAlreadyExistsException;
import com.ravifl.skilldna.user.mapper.UserMapper;
import com.ravifl.skilldna.user.repository.UserRepository;
import com.ravifl.skilldna.user.security.JwtService;
import com.ravifl.skilldna.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserResponse testUserResponse;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
            .id(UUID.randomUUID())
            .email("roy@example.com")
            .passwordHash("hashed_password")
            .firstName("Roy")
            .lastName("Ravi")
            .tier(SubscriptionTier.FREE)
            .build();

        testUserResponse = UserResponse.builder()
            .id(testUser.getId())
            .email(testUser.getEmail())
            .firstName(testUser.getFirstName())
            .lastName(testUser.getLastName())
            .tier(SubscriptionTier.FREE)
            .build();
    }

    @Test
    @DisplayName("register: success creates user and returns token")
    void register_success() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("roy@example.com");
        req.setPassword("Secret@123");
        req.setFirstName("Roy");
        req.setLastName("Ravi");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toResponse(testUser)).thenReturn(testUserResponse);
        when(jwtService.generateToken(any(), anyString(), anyString())).thenReturn("jwt-token");
        when(jwtService.getExpirationMs()).thenReturn(3600000L);

        AuthResponse result = userService.register(req);

        assertThat(result).isNotNull();
        assertThat(result.getAccessToken()).isEqualTo("jwt-token");
        assertThat(result.getTokenType()).isEqualTo("Bearer");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("register: throws UserAlreadyExistsException for duplicate email")
    void register_duplicateEmail_throws() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("roy@example.com");
        req.setPassword("Secret@123");
        req.setFirstName("Roy");
        req.setLastName("Ravi");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThatThrownBy(() -> userService.register(req))
            .isInstanceOf(UserAlreadyExistsException.class)
            .hasMessageContaining("roy@example.com");

        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("login: success returns token")
    void login_success() {
        LoginRequest req = new LoginRequest();
        req.setEmail("roy@example.com");
        req.setPassword("Secret@123");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.generateToken(any(), anyString(), anyString())).thenReturn("jwt-token");
        when(jwtService.getExpirationMs()).thenReturn(3600000L);
        when(userMapper.toResponse(testUser)).thenReturn(testUserResponse);

        AuthResponse result = userService.login(req);

        assertThat(result.getAccessToken()).isEqualTo("jwt-token");
    }

    @Test
    @DisplayName("login: wrong password throws BadCredentialsException")
    void login_wrongPassword_throws() {
        LoginRequest req = new LoginRequest();
        req.setEmail("roy@example.com");
        req.setPassword("WrongPass@1");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> userService.login(req))
            .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    @DisplayName("getProfile: returns user response for valid id")
    void getProfile_success() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(userMapper.toResponse(testUser)).thenReturn(testUserResponse);

        UserResponse result = userService.getProfile(testUser.getId());

        assertThat(result.getEmail()).isEqualTo("roy@example.com");
    }
}
