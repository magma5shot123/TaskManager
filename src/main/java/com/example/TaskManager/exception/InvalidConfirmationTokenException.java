package com.example.TaskManager.exception;

public class InvalidConfirmationTokenException extends RuntimeException {

    public InvalidConfirmationTokenException() {
        super("Invalid confirmation token");
    }

}
