package com.example.demo.models;

import com.example.demo.models.enums.Priority;
import com.example.demo.models.enums.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Setter
    @Column(nullable = false, length = 100)
    private String title;

    @Setter
    @Column(nullable = false, length = 2048)
    private String description;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Setter
    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Setter
    @Column(nullable = true)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User executor;

    public Task(String title, String description, State state, Priority priority, User author, User executor) {
        this.title = title;
        this.description = description;
        if (state != null) {
            this.state = state;
        } else {
            this.state = State.PENDING;  // по умолчанию "в ожидании"
        }
        if (priority != null) {
            this.priority = priority;
        } else {
            this.priority = Priority.LOW; // допустим, что это тоже может быть полем по умолчанию
        }
        this.author = author;
        this.executor = executor;
    }

}
