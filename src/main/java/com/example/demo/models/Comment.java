package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Table(name = "comments")
public class Comment {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "commenter_id", nullable = false)
    private User commenter;

    @Getter
    @Column(name = "left_at", nullable = false)
    private final LocalDateTime leftAt = LocalDateTime.now();

}
