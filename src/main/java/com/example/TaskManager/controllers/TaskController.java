package com.example.TaskManager.controllers;

import com.example.TaskManager.dto.task.*;
import com.example.TaskManager.entity.Task;
import com.example.TaskManager.repositories.TaskRepository;
import com.example.TaskManager.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/createTask")
    public ResponseEntity<TaskResponse<Task>> createTask(
            @Valid @RequestBody CreateTask dto
    ) {

        log.info("Create task request received");

        TaskResponse<Task> taskResponse = taskService.createTask(dto);

        log.info("Task successfully created");

        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskResponse<Task>> deleteTask(
            @PathVariable Long taskId
    ) {

        log.warn("Delete task request received: id={}", taskId);

        TaskResponse<Task> taskResponse = taskService.deleteTask(new DeleteTask(taskId));

        return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse<Task>> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateTask dto
    ) {

        log.info("Update task request received: id={}", taskId);

        TaskResponse<Task> taskResponse = taskService.updateTask(dto, taskId);

        return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse<Task>> getTask(
            @PathVariable Long taskId
    ) {

        log.info("Get task request received: id={}", taskId);

        TaskResponse<Task> taskResponse = taskService.getTask(new GetTask(taskId));

        return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
    }

}
