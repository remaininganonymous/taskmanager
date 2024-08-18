package com.example.demo.services;

import com.example.demo.DTO.CreateTaskRequest;
import com.example.demo.DTO.UpdateTaskRequest;
import com.example.demo.models.Task;
import com.example.demo.repositories.TaskRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskManagementServiceImpl implements TaskManagementService {

    private TaskRepository taskRepository;

    private UserInfoService userInfoService;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setUserInfoService(UserInfoServiceImpl userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public Task update(UpdateTaskRequest request) {
        return taskRepository.findById(request.getTaskId())
                .map(existingTask -> {
                    if (request.getTaskTitle() != null) existingTask.setTitle(request.getTaskTitle());
                    if (request.getTaskDescription() != null) existingTask.setDescription(request.getTaskDescription());
                    if (request.getTaskState() != null) existingTask.setState(request.getTaskState());
                    if (request.getTaskPriority() != null) existingTask.setPriority(request.getTaskPriority());
                    if (request.getTaskExecutorId() != null) existingTask.setExecutor(userInfoService.getById(request.getTaskExecutorId())
                            .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID " + request.getTaskExecutorId() + " не найден")));
                    return taskRepository.save(existingTask);
                })
                .orElseThrow(() -> new EntityNotFoundException("Задача с ID " + request.getTaskId() + " не найдена"));
    }

    @Override
    public Task create(CreateTaskRequest request) {
        Task task = Task.builder()
                .title(request.getTaskTitle())
                .description(request.getTaskDescription())
                .state(request.getTaskState())
                .priority(request.getTaskPriority())
                .author(userInfoService.getCurrentUser()
                        .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Вы не авторизованы")))
                .executor(userInfoService.getById(request.getTaskExecutorId())
                        .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID " + request.getTaskExecutorId() + " не найден")))
                .build();
        if (!taskRepository.existsByTitle(task.getTitle())) {
            return taskRepository.save(task);
        } else {
            throw new EntityExistsException("Такая задача уже существует");
        }
    }

    @Override
    public void delete(UUID id) {
        if (!taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Задача, которую вы хотели удалить, не была найдена");
        }

    }

}
