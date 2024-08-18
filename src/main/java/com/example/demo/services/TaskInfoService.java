package com.example.demo.services;

import com.example.demo.models.Task;
import com.example.demo.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskInfoService {

    public Optional<Task> getByTitle(String title);

    public Optional<Task> getById(UUID id);

    public List<Task> getAll();

    public List<Task> getByAuthorId(UUID id);

    public List<Task> getByExecutorId(UUID id);

}
