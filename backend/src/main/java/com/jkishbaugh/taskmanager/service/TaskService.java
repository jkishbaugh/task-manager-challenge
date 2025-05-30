package com.jkishbaugh.taskmanager.service;

import com.jkishbaugh.taskmanager.dto.TaskRequest;
import com.jkishbaugh.taskmanager.dto.TaskResponse;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskResponse createTask(TaskRequest request);
    List<TaskResponse> getAllTasks();
    TaskResponse updateTask(UUID id, TaskRequest request);
    void deleteTask(UUID id);
}
