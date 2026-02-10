package com.example.TaskManager.exception;

import lombok.Getter;

@Getter
public class EmailSendFailedException extends RuntimeException {

    private final String email;

    public EmailSendFailedException(String email, Throwable cause) {
        super("Failed to send email to " + email, cause);
        this.email = email;
    }
}
