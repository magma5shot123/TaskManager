package com.example.TaskManager.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "ConfirmTokenRequest",
        description = "DTO для подтверждения токена активации пользователя"
)
public record ConfirmTokenRequest(

        @Schema(
                description = "Токен подтверждения, полученный по email или ссылке",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        )
        @NotBlank
        String token

) {
}
