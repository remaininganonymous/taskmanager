package com.example.demo.services;

import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserRepository userRepository;

    private TaskInfoServiceImpl taskInfoService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private void setTaskInfoService(TaskInfoServiceImpl taskInfoService) {
        this.taskInfoService = taskInfoService;
    }

    @Override
    public Optional<User> getById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDetailsService userDetailsService() { //к удалению
        return email -> {
            Optional<User> optionalUser = getByEmail(email);
            return optionalUser.map(this::mapToUserDetails)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь с почтой  " + email + " не найден"));
        };
    }

    public Optional<User> getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(email);
    }

    private UserDetails mapToUserDetails(User user) { // и это тоже
        // Здесь вы преобразуете ваш объект User в объект UserDetails
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь с почтой %s не найден", username)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    /*
    * для предавторизации
    * */

    public boolean isAuthor(UUID id, String email) {
        Task task = taskInfoService.getById(id).orElse(null);
        if (task != null && task.getAuthor().getEmail() == email) return true;
        return false;
    }

    public boolean isExecutor(UUID id, String email) {
        Task task = taskInfoService.getById(id).orElse(null);
        if (task != null && task.getExecutor().getEmail() == email) return true;
        return false;
    }
}
