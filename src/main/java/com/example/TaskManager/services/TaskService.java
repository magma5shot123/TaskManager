package com.example.TaskManager.services;

import com.example.TaskManager.dto.task.*;
import com.example.TaskManager.entity.Task;
import com.example.TaskManager.entity.User;
import com.example.TaskManager.enums.TaskStatus;
import com.example.TaskManager.repositories.TaskRepository;
import com.example.TaskManager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public TaskResponse<Task> createTask(CreateTask dto) {
        Long currentUserId = getCurrentUserId();

        log.info(
                "User [{}] is creating task: title='{}', priority={}",
                currentUserId,
                dto.title(),
                dto.priority()
        );



        Task task = Task.builder()
                .title(dto.title())
                .description(dto.description())
                .taskStatus(TaskStatus.IN_PROGRESS)
                .taskPriority(dto.priority())
                .build();

        Task createdTask = taskRepository.save(task);

        log.info(
                "Task [{}] created by user [{}]",
                createdTask.getId(),
                createdTask.getCreatedBy()
        );

        return TaskResponse.success(
                createdTask,
                "Task created successfully",
                org.springframework.http.HttpStatus.CREATED
        );
    }

    @PreAuthorize("@taskSecurity.isOwner(#id)")
    @Transactional
    public TaskResponse<Task> updateTask(@NonNull UpdateTask dto, Long id) {

        Long currentUserId = getCurrentUserId();

        log.info(
                "User [{}] attempts to update task [{}]",
                currentUserId,
                id
        );

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Task [{}] not found for update", id);
                    return new IllegalArgumentException("Task not found with id: " + id);
                });

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setTaskStatus(dto.status());
        task.setTaskPriority(dto.priority());

        Task updatedTask = taskRepository.save(task);

        log.info(
                "Task [{}] successfully updated by user [{}]",
                updatedTask.getId(),
                currentUserId
        );

        return TaskResponse.success(
                updatedTask,
                "Task updated successfully",
                org.springframework.http.HttpStatus.OK
        );
    }

    @PreAuthorize("@taskSecurity.isOwner(#dto.id)")
    @Transactional
    public TaskResponse<Task> deleteTask(@NonNull DeleteTask dto) {

        Long currentUserId = getCurrentUserId();

        log.warn(
                "User [{}] attempts to DELETE task [{}]",
                currentUserId,
                dto.id()
        );

        Task choosenTask = taskRepository.findById(dto.id())
                .orElseThrow(() -> {
                    log.warn("Task [{}] not found for delete", dto.id());
                    return new IllegalArgumentException("Task not found with id: " + dto.id());
                });

        taskRepository.delete(choosenTask);

        log.warn(
                "Task [{}] deleted by user [{}]",
                choosenTask.getId(),
                currentUserId
        );

        return TaskResponse.success(
                choosenTask,
                "Task deleted successfully",
                org.springframework.http.HttpStatus.OK
        );
    }

    @PreAuthorize("@taskSecurity.isOwner(#dto.id)")
    @Transactional(readOnly = true)
    public TaskResponse<Task> getTask(@NonNull GetTask dto) {

        Long currentUserId = getCurrentUserId();

        log.info(
                "User [{}] attempts to get task [{}]",
                currentUserId,
                dto.id()
        );

        Task choosenTask = taskRepository.findById(dto.id())
                .orElseThrow(() -> {
                    log.warn("Task [{}] not found for get", dto.id());
                    return new IllegalArgumentException("Task not found with id: " + dto.id());
                });

        log.info(
                "Task [{}] successfully gotten by user [{}]",
                choosenTask.getId(),
                currentUserId
        );

        return TaskResponse.success(
                choosenTask,
                "Task found successfully",
                org.springframework.http.HttpStatus.OK
        );
    }



    @Transactional
    protected Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new BadCredentialsException("No authenticated user");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof User user) {
            return user.getId();
        } else if (principal instanceof UserDetails userDetails) {
            return ((User) userDetails).getId();
        }

        throw new IllegalStateException("Cannot extract user ID from principal");
    }
}
