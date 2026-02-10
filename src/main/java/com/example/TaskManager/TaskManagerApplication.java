package com.example.TaskManager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@EnableAsync
@SpringBootApplication
@EnableJpaAuditing
public class TaskManagerApplication {

	public static void main(String[] args) {
		log.info("Starting TaskManagerApplication");
		SpringApplication.run(TaskManagerApplication.class, args);
	}

}
