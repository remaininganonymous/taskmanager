package com.example.demo.services;

import com.example.demo.DTO.request.SignUpRequest;
import com.example.demo.DTO.request.UpdateUserRequest;
import com.example.demo.models.User;

import java.util.UUID;

public interface UserManagementService {

    public User update(UpdateUserRequest request);

    public User create(SignUpRequest request);

    public void delete(UUID id);

}
