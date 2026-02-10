package com.example.TaskManager.repositories;

import com.example.TaskManager.entity.Task;
import com.example.TaskManager.enums.TaskPriority;
import com.example.TaskManager.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repository для работы с сущностью Task.
 * Используется Spring Data JPA для CRUD операций.
 *
 * Дополнительно определены методы для поиска задач:
 * - по статусу
 * - по приоритету
 * - по создателю
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String taskTitle);

    List<Task> findByTaskStatus(TaskStatus status);
    List<Task> findByTaskPriority(TaskPriority priority);
    List<Task> findByCreatedById(Long userId);
    List<Task> findByTaskStatusAndTaskPriority(TaskStatus status, TaskPriority priority);

    boolean existsByTaskStatusAndTaskPriority(TaskStatus status, TaskPriority priority);
    boolean existsById(Long id);
    boolean existsByIdAndCreatedById(Long taskId, Long userId);

}
