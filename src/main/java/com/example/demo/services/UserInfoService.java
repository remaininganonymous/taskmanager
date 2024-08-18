package com.example.demo.services;

import com.example.demo.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserInfoService {

    public Optional<User> getById(UUID id);

    public Optional<User> getByEmail(String email);

    /*public UserDetailsService userDetailsService();*/

    public Optional<User> getCurrentUser();

}
