package com.example.demo.services;

import com.example.demo.DTO.request.CreateTaskRequest;
import com.example.demo.DTO.request.UpdateTaskRequest;
import com.example.demo.models.Task;

import java.util.UUID;

public interface TaskManagementService {

    public Task update(UpdateTaskRequest request);

    public Task create(CreateTaskRequest request);

    public void delete(UUID id);

}
