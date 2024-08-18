package com.example.demo.services;

import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskInfoServiceImpl implements TaskInfoService {

    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> getByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    @Override
    public Optional<Task> getById(UUID id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getByAuthorId(UUID id) {
        return taskRepository.findByAuthorId(id);
    }

    @Override
    public List<Task> getByExecutorId(UUID id) {
        return taskRepository.findByExecutorId(id);
    }

}
