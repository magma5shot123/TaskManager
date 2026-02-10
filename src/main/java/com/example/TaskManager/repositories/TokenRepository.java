package com.example.TaskManager.repositories;

import com.example.TaskManager.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);
    Optional<ConfirmationToken> findByUserEmailAndCode(String userEmail, String code);

}
