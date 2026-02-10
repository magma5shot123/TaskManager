package com.example.TaskManager.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


/*
DTO - для удаления задачи по её ID

ID - номер задачи в бд, обязательно
 */

@Schema(
        name = "DeleteTask",
        description = "DTO для удаления задачи по её ID"
)
public record DeleteTask(

        @Schema(
                description = "ID задачи в базе данных",
                example = "123",
                required = true
        )
        @NotNull(message = "ID can't be null")
        @Positive(message = "ID should be positive")
        Long id

) {
}
