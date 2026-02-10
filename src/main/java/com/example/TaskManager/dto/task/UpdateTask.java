package com.example.TaskManager.dto.task;

import com.example.TaskManager.enums.TaskPriority;
import com.example.TaskManager.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;


/*
DTO - для обновления существующей задачи

ID - номер существующей задачи
Title - название задачи, обязательно
Description - описание задачи, обязательно
Priority - приоритет задачи, обязательно
Status - статус задачи, обязательно
 */

@Schema(
        name = "UpdateTask",
        title = "Обновление задачи",
        description = "DTO для обновления существующей задачи",
        requiredProperties = {"task_title", "task_priority", "task_status"}
)
public record UpdateTask(

        @Schema(
                description = "Название задачи",
                example = "Сделать рефакторинг кода",
                required = true
        )
        @NotBlank(message = "Title can't be blank")
        @Size(max = 100, message = "Title can't be longer than 100 characters")
        @JsonProperty("task_title")
        @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "Title can't start or end with whitespace")
        String title,

        @Schema(
                description = "Описание задачи",
                example = "Нужно переписать метод updateTask для оптимизации",
                required = false
        )
        @Size(max = 500, message = "Description can't be longer than 500 characters")
        @JsonProperty("task_description")
        String description,

        @Schema(
                description = "Приоритет задачи",
                example = "HIGH",
                required = true
        )
        @NotNull(message = "Priority can't be null")
        @JsonProperty("task_priority")
        TaskPriority priority,

        @Schema(
                description = "Статус задачи",
                example = "IN_PROGRESS",
                required = true
        )
        @NotNull(message = "Status can't be null")
        @JsonProperty("task_status")
        TaskStatus status


) {
}
