package com.jkishbaugh.taskmanager.service;

import com.jkishbaugh.taskmanager.dto.TaskRequest;
import com.jkishbaugh.taskmanager.dto.TaskResponse;
import com.jkishbaugh.taskmanager.model.Task;
import com.jkishbaugh.taskmanager.model.TaskStatus;
import com.jkishbaugh.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCreatedTask() {
        TaskRequest testRequest = TaskRequest.builder()
                .title("Test Title")
                .description(" This is a test description")
                .taskStatus(TaskStatus.TODO)
                .build();

        Task task = Task.builder()
                .id(UUID.randomUUID())
                .title(testRequest.getTitle())
                .description(testRequest.getDescription())
                .taskStatus(testRequest.getTaskStatus())
                .build();
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        var result = taskService.createTask(testRequest);

        assertEquals(testRequest.getTitle(), result.getTitle());
        assertEquals(testRequest.getDescription(), result.getDescription());
        assertEquals(testRequest.getTaskStatus(), result.getTaskStatus());
    }

    @Test
    void getAllTasks_ShouldReturnList() {
        List<Task> testTaskList = List.of(
                new Task(UUID.randomUUID(), "Title 1", "Desc 1", TaskStatus.TODO),
                new Task(UUID.randomUUID(), "Title 2", "Desc 2", TaskStatus.IN_Progress)
        );

        when(taskRepository.findAll()).thenReturn(testTaskList);

        var results = taskService.getAllTasks();

        assertEquals(2, results.size());
    }

    @Test
    void updateTask_ShouldUpdateAndReturnTask() {
        UUID testId = UUID.randomUUID();
        Task existing = new Task(testId, "Original Title", "", TaskStatus.TODO);
        TaskRequest updateRequest = new TaskRequest("Amazing New Title", "This is a description for the task in question", TaskStatus.IN_Progress);

        when(taskRepository.findById(testId)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenReturn(existing);

        var result = taskService.updateTask(testId, updateRequest);

        assertEquals("Amazing New Title", result.getTitle());
        assertEquals("This is a description for the task in question", result.getDescription());
        assertEquals(TaskStatus.IN_Progress, result.getTaskStatus());

    }

    @Test
    void deleteTask_ShouldCallRepository() {
        UUID testId = UUID.randomUUID();
        doNothing().when(taskRepository).deleteById(testId);
        taskService.deleteTask(testId);
        verify(taskRepository).deleteById(testId);
    }
}