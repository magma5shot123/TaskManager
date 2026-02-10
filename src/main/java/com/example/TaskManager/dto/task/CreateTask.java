package com.example.TaskManager.dto.task;

import com.example.TaskManager.enums.TaskPriority;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


/*
DTO - для создания новой задачи

Title - название задачи, обязательно
Description - описание задачи, обязательно
Priority - приоритет задачи, обязательно
 */

@Schema(
        name = "CreateTask",
        description = "DTO для создания новой задачи"
)
public record CreateTask(

        @Schema(
                description = "Название задачи",
                example = "Сделать отчёт по проекту",
                maxLength = 100,
                required = true
        )
        @NotBlank(message = "Title can't be blank")
        @Size(max = 100, message = "Title can't be longer than 100 characters")
        @JsonProperty("task_title")
        @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "Title can't start or end with whitespace")
        String title,

        @Schema(
                description = "Описание задачи",
                example = "Необходимо собрать все данные по проекту и оформить отчёт",
                maxLength = 500
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
        TaskPriority priority

) {
}
