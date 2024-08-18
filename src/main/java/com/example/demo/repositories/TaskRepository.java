package com.example.demo.repositories;

import com.example.demo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    boolean existsByTitle(String title);

    Optional<Task> findByTitle(String title);

    List<Task> findByAuthorId(UUID id);

    List<Task> findByExecutorId(UUID id);

}
