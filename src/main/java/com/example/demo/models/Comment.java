package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String text;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commenter;

    @Column(nullable = false)
    private LocalDateTime leftAt;

    @Setter
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    public Comment(String text, User commenter, LocalDateTime leftAt) {
        this.text = text;
        if (leftAt != null) {
            this.leftAt = leftAt;
        } else {
            this.leftAt = LocalDateTime.now();
        }
        this.commenter = commenter;
    }
}
