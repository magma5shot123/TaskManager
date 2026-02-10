package com.example.TaskManager.sequrities.confirmationToken;

import java.security.SecureRandom;
import java.util.UUID;

public class TokenGenerator {

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static String generateCode() {
        return String.valueOf(
                100000 + new SecureRandom().nextInt(900000)
        );
    }

}
