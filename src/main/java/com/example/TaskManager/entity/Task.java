package com.example.TaskManager.entity;

import com.example.TaskManager.enums.TaskPriority;
import com.example.TaskManager.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


/**
 * Entity, представляющая задачу (Task) в системе TaskManager.
 *
 * Поля:
 * - id           : уникальный идентификатор задачи (Primary Key, auto-increment)
 * - title        : название задачи, обязательно, до 100 символов
 * - description  : описание задачи, не обязательно, до 500 символов
 * - taskStatus   : статус задачи (IN_PROGRESS, DONE и т.д.), хранится как строка
 * - taskPriority : приоритет задачи (LOW, MEDIUM, HIGH), хранится как строка
 *
 * Особенности:
 * - Автоматическое управление датами через методы @PrePersist и @PreUpdate
 * - Используется Lombok для генерации getter'ов, конструктора, toString и Builder
 * - Таблица в базе данных называется "tasks"
 */


@Entity
@ToString
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Table (name = "tasks")
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    @Setter
    private String title;

    @Column(name = "description", length = 500)
    @Setter
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    @Setter
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_priority", nullable = false)
    @Setter
    private TaskPriority taskPriority;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_id", nullable = false)
    @Setter
    private User createdBy;
}
