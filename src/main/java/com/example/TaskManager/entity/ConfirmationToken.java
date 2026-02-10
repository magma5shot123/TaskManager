package com.example.TaskManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Setter
    private String token;

    @NotBlank
    @Setter
    private String code;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime expiresAt;

    @Setter
    private LocalDateTime confirmedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private User user;

}
