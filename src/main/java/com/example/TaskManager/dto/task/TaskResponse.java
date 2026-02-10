package com.example.TaskManager.dto.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;



/**
 * DTO для ответа сервера об успехе создания/удаления/... задачи
 *
 * @param <T> тип данных (например, TaskDTO)
 */

@Schema(
        name = "TaskResponse",
        description = "Ответ сервера с данными задачи"
)
public record TaskResponse<T> (

        @Schema(
                description = "HTTP статус код",
                example = "200"
        )
        @JsonProperty("status")
        int status,

        @Schema(
                description = "Сообщение от сервера",
                example = "Task created successfully"
        )
        @JsonProperty("message")
        String message,

        @Schema(
                description = "Объект с данными, может быть null"
        )
        @JsonProperty("object")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data

) {

    public static <T> TaskResponse<T> success(T data, String message, HttpStatus httpStatus) {
        return new TaskResponse<>(httpStatus.value(), message, data);
    }

    public static <T> TaskResponse<T> error(String message, HttpStatus httpStatus) {
        return new TaskResponse<>(httpStatus.value(), message, null);
    }

}
