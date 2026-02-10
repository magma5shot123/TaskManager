package com.example.TaskManager.services;

import com.example.TaskManager.dto.auth.AuthResponse;
import com.example.TaskManager.dto.auth.LoginRequest;
import com.example.TaskManager.dto.auth.RegisterRequest;
import com.example.TaskManager.entity.ConfirmationToken;
import com.example.TaskManager.entity.User;
import com.example.TaskManager.enums.Role;
import com.example.TaskManager.exception.ConfirmationTokenExpiredException;
import com.example.TaskManager.exception.InvalidConfirmationTokenException;
import com.example.TaskManager.exception.UserAlreadyConfirmedException;
import com.example.TaskManager.repositories.TokenRepository;
import com.example.TaskManager.repositories.UserRepository;
import com.example.TaskManager.sequrities.JWT.JwtService;

import com.example.TaskManager.sequrities.confirmationToken.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;


    @Transactional(readOnly = true)
    public AuthResponse login(@NonNull LoginRequest dto) {

        log.info("Login attempt: login={}", dto.login());

        User user = userRepository.findByEmailOrUsername(dto.login(), dto.login())
                .orElseThrow(() -> {
                    log.warn("Login failed: invalid credentials");
                    return new BadCredentialsException("Invalid email or password");
                });

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            log.warn("Login failed: invalid credentials");
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        log.info("Login successful: userId={}, username={}",
                user.getId(), user.getUsername());

        log.debug("JWT generated for userId={}", user.getId());

        return new AuthResponse(token);
    }

    @Transactional
    public AuthResponse register(@NonNull RegisterRequest dto) {

        log.info("Registration attempt: username={}, email={}",
                dto.username(), dto.email());

        if (userRepository.existsByEmail(dto.email())) {
            log.warn("Registration failed: invalid credentials");
            throw new BadCredentialsException("Invalid email or password");
        }
        if (userRepository.existsByUsername(dto.username())) {
            log.warn("Registration failed: invalid credentials");
            throw new BadCredentialsException("Invalid email or password");
        }

        User user = User.builder()
                .email(dto.email())
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .enabled(false)
                .build();

        userRepository.save(user);

        log.info("User created: userId={}, username={}", user.getId(), user.getUsername());

        String tokenEmail = TokenGenerator.generateToken();
        String codeEmail = TokenGenerator.generateCode();

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(tokenEmail)
                .code(codeEmail)
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .build();

        tokenRepository.save(confirmationToken);

        log.debug("Confirmation token generated: userId={}, expiresAt={}",
                user.getId(), confirmationToken.getExpiresAt());

        emailService.sendConfirmationEmail(
                user.getEmail(),
                codeEmail,
                tokenEmail
        );

        log.info("Confirmation email sent: userId={}, email={}",
                user.getId(), user.getEmail());

        String token = jwtService.generateToken(user);

        log.debug("JWT generated after registration: userId={}", user.getId());

        return new AuthResponse(token);
    }


    @Transactional
    public void confirmByToken(String token) {

        log.info("Email confirmation by token started");

        ConfirmationToken ct = tokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.warn("Confirmation failed: invalid token");
                    return new InvalidConfirmationTokenException();
                });

        if (ct.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.warn("Confirmation failed: token expired");
            throw new ConfirmationTokenExpiredException();
        }

        activateUser(ct);
    }

    @Transactional
    public void confirmByCode(String email, String code) {

        log.info("Email confirmation by code started: email={}", email);

        ConfirmationToken ct = tokenRepository.findByUserEmailAndCode(email, code)
                .orElseThrow(() -> {
                    log.warn("Confirmation failed: invalid code, email={}", email);
                    return new InvalidConfirmationTokenException();
                });

        if (ct.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.warn("Confirmation failed: code expired, email={}", email);
            throw new ConfirmationTokenExpiredException();
        }

        activateUser(ct);

    }

    private void activateUser(@NonNull ConfirmationToken ct) {
        User user = ct.getUser();

        if (ct.getConfirmedAt() != null) {
            log.warn("User already confirmed: userId={}", user.getId());
            throw new UserAlreadyConfirmedException();
        }

        ct.setConfirmedAt(LocalDateTime.now());
        tokenRepository.save(ct);


        user.setEnabled(true);
        userRepository.save(user);

        log.info("User successfully activated: userId={}, username={}",
                user.getId(), user.getUsername());
    }

}
