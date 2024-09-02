package com.example.demo.models;

import com.example.demo.models.enums.Priority;
import com.example.demo.models.enums.State;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Entity
@Table(name = "tasks")
public class Task {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Getter
    @Setter
    @Column(nullable = false, length = 100)
    private String title;

    @Getter
    @Setter
    @Column(nullable = false, length = 2048)
    private String description;

    @Getter
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Getter
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;

    @Getter
    @Setter
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
