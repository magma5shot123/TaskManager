package com.example.TaskManager.dto.auth;

import com.example.TaskManager.customAnnotation.PasswordMatches;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@PasswordMatches
@Schema(
        name = "RegisterRequest",
        description = " ДТО для регистрации пользователя",
        requiredProperties = {"username", "email", "password", "confirmPassword"}
)
public record RegisterRequest (

        @Schema(
                title = "Имя пользователя",
                description = "Имя пользователя в системе. Допустимые символы: буквы, цифры, '.', '_', '-'. Длина: 4–18 символов.",
                examples = {"john_je", "Alica_10", "max_pen"}
        )
        @NotBlank(message = "Username can't be blank")
        @Pattern(
                regexp = "^[a-zA-Z0-9._-]+$",
                message = "Username must contain at least one uppercase letter or one lowercase letter"
        )
        @Size(min = 4, max = 18, message = "Username must be between 4 and 18 characters")
        String username,


        @Schema(
                title = "Email пользователя",
                description = "Email пользователя",
                examples = {"max_pen@gmail.com"},
                format = "email"
        )
        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        String email,


        @Schema(
                title = "Пароль",
                description = "Пароль пользователя. Минимум 12 символов, должен содержать хотя бы одну заглавную букву, одну строчную, одну цифру и один спецсимвол (@#$%^&+=!).",
                examples = {"1234fdsvcSASDC~!", "vv__d456@#WW", "Dima2342!!!@x"},
                format = "password",
                accessMode = Schema.AccessMode.WRITE_ONLY
        )
        @NotBlank(message = "Password can't be blank")
        @Size(min = 12, max = 32, message = "Invalid password")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character"
        )
        String password,


        @Schema(
                title = "Подтверждение пароля",
                description = "Подтверждение пароля. Должно совпадать с полем 'password'.",
                examples = {"1234fdsvcSASDC~!", "vv__d456@#WW", "Dima2342!!!@x"},
                format = "password",
                accessMode = Schema.AccessMode.WRITE_ONLY
        )
        @NotBlank(message = "Confirm password is required")
        String confirmPassword
){}
