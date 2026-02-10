package com.example.TaskManager.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "AuthResponse",
        description = "Ответ при успешной аутентификации пользователя"
)
public record AuthResponse (

        @Schema(
                description = "JWT токен доступа",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        String accessToken,

        @Schema(
                description = "Тип токена",
                example = "Bearer",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        String tokenType

) {

    public AuthResponse (String accessToken) {
        this(accessToken, "Bearer");
    }

}
