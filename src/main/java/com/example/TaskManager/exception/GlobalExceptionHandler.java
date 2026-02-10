package com.example.TaskManager.exception;

import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(@NonNull UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "USER_NOT_FOUND",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(@NonNull BadCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "INVALID_CREDENTIALS",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    @ExceptionHandler(InvalidConfirmationTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidToken(@NonNull InvalidConfirmationTokenException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_CONFIRMATION_TOKEN",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(ConfirmationTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleExpiredToken(@NonNull ConfirmationTokenExpiredException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.GONE.value(),
                "CONFIRMATION_TOKEN_EXPIRED",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return  ResponseEntity
                .status(HttpStatus.GONE)
                .body(error);
    }

    @ExceptionHandler(UserAlreadyConfirmedException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyConfirmed(@NonNull UserAlreadyConfirmedException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "USER_ALREADY_CONFIRMED",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return   ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }


    @ExceptionHandler(EmailSendFailedException.class)
    public ResponseEntity<ErrorResponse> handleEmailSendFailed(@NonNull EmailSendFailedException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "EMAIL_SEND_FAILED",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return   ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }


}
