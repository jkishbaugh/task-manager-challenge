package com.jkishbaugh.taskmanager.dto;

import com.jkishbaugh.taskmanager.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequest {
    @NotBlank
    private String title;
    private String description;
    private TaskStatus taskStatus;
}
