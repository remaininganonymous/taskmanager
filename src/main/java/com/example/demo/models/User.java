package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @OneToMany(mappedBy = "author")
    private List<Task> authorOf = Collections.emptyList();

    @Setter
    @OneToMany(mappedBy = "executor")
    private List<Task> executorOf = Collections.emptyList();

    @Setter
    @OneToMany(mappedBy = "commenter")
    private List<Comment> comments = Collections.emptyList();

    public User(String email, String password, List<Task> tasksUserAuthorOf, List<Task> tasksUserExecutorOf, List<Comment> comments) {
        this.email = email;
        this.password = password;
    }
}
