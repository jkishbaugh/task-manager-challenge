package com.jkishbaugh.taskmanager.service;

import com.jkishbaugh.taskmanager.dto.TaskRequest;
import com.jkishbaugh.taskmanager.dto.TaskResponse;
import com.jkishbaugh.taskmanager.model.Task;
import com.jkishbaugh.taskmanager.model.TaskStatus;
import com.jkishbaugh.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;

    @Override
    public TaskResponse createTask(TaskRequest taskRequest){
        System.out.println(taskRequest);
        Task task = Task.builder().id(UUID.randomUUID())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .taskStatus(taskRequest.getTaskStatus() != null ? taskRequest.getTaskStatus(): TaskStatus.TODO)
                .build();
        taskRepository.save(task);
        System.out.println(mapToResponse(task));
        return mapToResponse(task);
    }



    @Override
    public List<TaskResponse> getAllTasks(){
        return taskRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public TaskResponse updateTask(UUID id, TaskRequest taskRequest){
            Task task = taskRepository.findById(id).orElseThrow();
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setTaskStatus(taskRequest.getTaskStatus());
            taskRepository.save(task);
            return mapToResponse(task);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder().id(task.getId())
                .title(task.getTitle())
                .taskStatus(task.getTaskStatus())
                .description(task.getDescription())
                .build();
    }
}
