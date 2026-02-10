package com.example.TaskManager.sequrities;

import com.example.TaskManager.entity.User;
import com.example.TaskManager.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("taskSecurity")
@RequiredArgsConstructor
public class TaskSecurity {

    private final TaskRepository taskRepository;


    public boolean isOwner(Long taskId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        User user = (User) authentication.getPrincipal();

        assert user != null;
        return taskRepository.existsByIdAndCreatedById(taskId, user.getId());

    }

}
