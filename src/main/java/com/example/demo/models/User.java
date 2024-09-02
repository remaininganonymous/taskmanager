package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @Getter
    @Setter
    @Column(nullable = false)
    private String email;

    @Getter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @OneToMany(mappedBy = "author")
    private List<Task> authorOf;

    @Getter
    @Setter
    @OneToMany(mappedBy = "executor")
    private List<Task> executorOf;

    @Getter
    @Setter
    @OneToMany(mappedBy = "commenter")
    private List<Comment> comments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    /*public void setPassword(String password) {
        this.password = password;
    }*/

}
