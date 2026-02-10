package com.example.TaskManager.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "ConfirmCodeRequest",
        description = "DTO для подтверждения email через код"
)
public record ConfirmCodeRequest(

        @Schema(
                description = "Email пользователя, на который был отправлен код",
                example = "user@example.com"
        )
        @Email
        @NotBlank
        String email,

        @Schema(
                description = "Код подтверждения, отправленный на email",
                example = "123456"
        )
        @NotBlank
        @Size(min = 6, max = 6)
        String code

) {
}
