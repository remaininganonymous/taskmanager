package com.example.demo.services;

import com.example.demo.DTO.CreateUserRequest;
import com.example.demo.DTO.UpdateUserRequest;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User update(UpdateUserRequest request) {
        return userRepository.findById(request.getUserId())
                .map(existingUser -> {
                    existingUser.setEmail(request.getUserEmail());
                    existingUser.setPassword(request.getUserPassword());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID " + request.getUserId() + " не найден"));
    }

    @Override
    public User create(CreateUserRequest request) {
        User user = User.builder()
                .email(request.getUserEmail())
                .password(request.getUserPassword())
                .build();
        if (!userRepository.existsByEmail(request.getUserEmail())) {
            return userRepository.save(user);
        } else {
            throw new EntityExistsException("Пользователь с таким email уже существует");
        }
    }

    @Override
    public void delete(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Пользователь с ID " + id + " не найден");
        }

    }
}
