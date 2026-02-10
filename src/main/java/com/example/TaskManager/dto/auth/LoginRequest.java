package com.example.TaskManager.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(
        name = "LoginRequest",
        description = "ДТО для логина пользователя",
        requiredProperties = {"login", "password"}
)
public record LoginRequest (

        @Schema(
                description = """
                        Имя пользователя или email для входа в систему.
                        Username: 4-18 символов, буквы, цифры, '.', '_' или '-'.
                        Email: стандартный формат email.
                    """,
                examples = {"john_je", "Alica_10", "max_pen@gmail.com"}
        )
        @NotBlank
        @Pattern(
                regexp = "^[a-zA-Z0-9._-]{4,18}$|^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$",
                message = "Login must be a valid username (4-18 chars) or a valid email"
        )
        @Size(min = 4, max = 18, message = "Username must be between 4 and 18 characters")
        String login,

        @Schema(
                description = """
                        Пароль пользователя.
                        Минимум 12 символов.
                        Должен содержать как минимум одну заглавную букву, одну строчную, одну цифру и один спецсимвол (@#$%^&+=!).
                    """,
                examples = {"1234fdsvcSASDC~!", "vv__d456@#WW", "Dima2342!!!@x"},
                format = "password",
                writeOnly = true
        )
        @NotBlank
        @Size(min = 12, max = 32, message = "Invalid password")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character"
        )
        String password
) {
}
