package com.example.demo.services;

import com.example.demo.DTO.request.SignUpRequest;
import com.example.demo.DTO.request.UpdateUserRequest;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public User create(SignUpRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        if (!userRepository.existsByEmail(request.getEmail())) {
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
