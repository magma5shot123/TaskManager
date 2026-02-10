package com.example.TaskManager.exception;

public class UserAlreadyConfirmedException extends RuntimeException {

    public UserAlreadyConfirmedException() {
        super("User already confirmed");
    }

}
