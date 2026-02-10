package com.example.TaskManager.exception;

public class ConfirmationTokenExpiredException extends RuntimeException {

    public ConfirmationTokenExpiredException() {
        super("Confirmation token has expired");
    }

}
