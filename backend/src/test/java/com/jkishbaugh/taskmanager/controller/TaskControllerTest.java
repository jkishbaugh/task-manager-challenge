package com.jkishbaugh.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkishbaugh.taskmanager.dto.TaskRequest;
import com.jkishbaugh.taskmanager.dto.TaskResponse;
import com.jkishbaugh.taskmanager.model.Task;
import com.jkishbaugh.taskmanager.model.TaskStatus;
import com.jkishbaugh.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService mockTaskService;

    @Test
    void shouldCreateTask() throws Exception{
        TaskRequest testRequest = new TaskRequest("Test", "Test Desc", TaskStatus.TODO);
        TaskResponse testResponse = TaskResponse.builder()
                .id(UUID.randomUUID())
                .title("Test")
                .description("Test Desc")
                .taskStatus(TaskStatus.TODO)
                .build();

        when(mockTaskService.createTask(any(TaskRequest.class))).thenReturn(testResponse);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test"));
    }

    @Test
    void shouldGetAllTasks() throws Exception {
        List<TaskResponse> testResponses = List.of(
                new TaskResponse(UUID.randomUUID(), "Test", "Test Desc", TaskStatus.TODO),
                new TaskResponse(UUID.randomUUID(), "Test 1", "Test 1 Desc", TaskStatus.TODO)
        );
        when(mockTaskService.getAllTasks()).thenReturn(testResponses);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateAndReturnTask() throws Exception{
        UUID testId = UUID.randomUUID();
        TaskResponse test = new TaskResponse(testId, "Updated", "Updated Desc", TaskStatus.DONE);

        when(mockTaskService.updateTask(eq(testId), any(TaskRequest.class))).thenReturn(test);

        mockMvc.perform(put("/tasks/{id}", testId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(test)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated"))
            .andExpect(jsonPath("$.taskStatus").value("DONE"));

    }

    @Test
    void testDelete() throws Exception {
        UUID testId = UUID.randomUUID();
        doNothing().when(mockTaskService).deleteTask(testId);

        mockMvc.perform(delete("/tasks/{id}", testId))
                .andExpect(status().isNoContent());
    }
}