package com.jkishbaugh.taskmanager.repository;

import com.jkishbaugh.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
