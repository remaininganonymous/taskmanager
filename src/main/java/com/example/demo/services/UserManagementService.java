package com.example.demo.services;

import com.example.demo.DTO.CreateUserRequest;
import com.example.demo.DTO.UpdateUserRequest;
import com.example.demo.models.User;

import java.util.UUID;

public interface UserManagementService {

    public User update(UpdateUserRequest request);

    public User create(CreateUserRequest request);

    public void delete(UUID id);

}
