package com.example.TaskManager.controllers;

import com.example.TaskManager.dto.auth.AuthResponse;
import com.example.TaskManager.dto.auth.LoginRequest;
import com.example.TaskManager.dto.auth.RegisterRequest;
import com.example.TaskManager.dto.token.ConfirmCodeRequest;
import com.example.TaskManager.dto.token.ConfirmTokenRequest;
import com.example.TaskManager.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        log.info(
                "Login request received: login='{}'",
                loginRequest.login()
        );

        AuthResponse authResponse = authService.login(loginRequest);

        log.info(
                "Login successful for '{}'",
                loginRequest.login()
        );

        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {

        log.info(
                "Register request received: email='{}', username='{}'",
                registerRequest.email(),
                registerRequest.username()
        );

        AuthResponse authResponse = authService.register(registerRequest);

        log.info(
                "Register successful for: username='{}'",
                registerRequest.username()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }


    @PostMapping("/confirm")
    public ResponseEntity<Void> confirm(
            @Valid @RequestBody ConfirmTokenRequest request
    ) {

        log.info("Account confirmation by token request received");

        authService.confirmByToken(request.token());

        log.info("Account successfully confirmed by token");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm/code")
    public ResponseEntity<Void> confirmByCode(
            @Valid @RequestBody ConfirmCodeRequest request
    ) {

        log.info(
                "Account confirmation by code: email='{}'",
                request.email()
        );

        authService.confirmByCode(request.email(), request.code());

        log.info(
                "Account successfully confirmed by code for email='{}'",
                request.email()
        );

        return ResponseEntity.ok().build();
    }




}
