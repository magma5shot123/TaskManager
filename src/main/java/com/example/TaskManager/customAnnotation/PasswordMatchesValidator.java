package com.example.TaskManager.customAnnotation;

import com.example.TaskManager.dto.auth.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object obj,  ConstraintValidatorContext context) {
        if (obj instanceof RegisterRequest request) {
            return request.password().equals(request.confirmPassword());
        }
        return false;
    }

}
