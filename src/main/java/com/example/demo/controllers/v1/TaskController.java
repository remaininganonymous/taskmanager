package com.example.demo.controllers.v1;

import com.example.demo.DTO.CreateTaskRequest;
import com.example.demo.DTO.LeaveCommentRequest;
import com.example.demo.DTO.UpdateTaskRequest;
import com.example.demo.datatypes.LabeledResponse;
import com.example.demo.models.Comment;
import com.example.demo.models.Task;
import com.example.demo.models.enums.Priority;
import com.example.demo.models.enums.State;
import com.example.demo.services.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@ControllerAdvice
@RequestMapping("/api/v1/task")
public class TaskController {

    private TaskInfoService taskInfoService;

    @Autowired
    public void setTaskInfoService(TaskInfoServiceImpl taskInfoService) {
        this.taskInfoService = taskInfoService;
    }

    private TaskManagementService taskManagementService;

    @Autowired
    public void setTaskManagementService(TaskManagementServiceImpl taskManagementService) {
        this.taskManagementService = taskManagementService;
    }

    private CommentInfoService commentInfoService;

    @Autowired
    private void setCommentInfoService(CommentInfoServiceImpl commentInfoService) {
        this.commentInfoService = commentInfoService;
    }

    private CommentManagementService commentManagementService;

    @Autowired
    private void setCommentManagementService(CommentManagementServiceImpl commentManagementService) {
        this.commentManagementService = commentManagementService;
    }

    public ResponseEntity<?> buildErrorResponse(Exception e, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(status.value() + status.getReasonPhrase() + ": " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return buildErrorResponse(e, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return buildErrorResponse(e, status);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody @Valid CreateTaskRequest request) {
        try {
            Task task = taskManagementService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new LabeledResponse("Созданная задача", task));
        } catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("401 UNAUTHORIZED: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("404 NOT FOUND: " + e.getMessage());
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("409 CONFLICT: " + e.getMessage()); // а может не надо?
        }
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<?> editTask(@PathVariable UUID id, @RequestBody @Valid UpdateTaskRequest request) {
        try {
            Task task = taskManagementService.update(request);
            return ResponseEntity.ok(new LabeledResponse("Обновленная задача", task));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("404 NOT FOUND: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> viewAllTasks() {
        List<Task> tasks = taskInfoService.getAll();
        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("В списке пока ни одного задания");
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<?> viewTasksByUser(@PathVariable UUID id) {
        LabeledResponse<List<Task>> tasksUserAuthorOf = new LabeledResponse("Автор заданий", taskInfoService.getByAuthorId(id));
        LabeledResponse<List<Task>> tasksUserExecutorOf = new LabeledResponse("Исполнитель заданий", taskInfoService.getByExecutorId(id));
        Set<LabeledResponse<List<Task>>> response = Set.of(tasksUserAuthorOf, tasksUserExecutorOf);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable UUID id) {
        try {
            taskManagementService.delete(id);
            return ResponseEntity.ok().body("Удаление прошло успешно");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("404 NOT FOUND: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/state")
    public ResponseEntity<?> changeState(@PathVariable UUID id, @RequestParam State state) {
        try {
            UpdateTaskRequest request = new UpdateTaskRequest();
            request.setTaskId(id);
            request.setTaskState(state);
            Task updatedTask = taskManagementService.update(request);
            return ResponseEntity.ok(new LabeledResponse("Обновленная задача", updatedTask));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("404 NOT FOUND: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<?> changePriority(@PathVariable UUID id, @RequestParam Priority priority) {
        try {
            UpdateTaskRequest request = new UpdateTaskRequest();
            request.setTaskId(id);
            request.setTaskPriority(priority);
            Task updatedTask = taskManagementService.update(request);
            return ResponseEntity.ok(new LabeledResponse("Обновленная задача", updatedTask));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("404 NOT FOUND: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<?> assignExecutor(@PathVariable UUID taskId, @RequestParam UUID executorToAssignId) {
        try {
            UpdateTaskRequest request = new UpdateTaskRequest();
            request.setTaskId(taskId);
            request.setTaskExecutorId(executorToAssignId);
            Task updatedTask = taskManagementService.update(request);
            return ResponseEntity.ok(new LabeledResponse("Обновленная задача", updatedTask));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("404 NOT FOUND: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> commentTask(@PathVariable UUID id, @RequestBody LeaveCommentRequest request) {
        try {
            Comment comment = commentManagementService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new LabeledResponse("Новый комментарий", comment));
        } catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("401 UNAUTHORIZED: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getCommentsOnTask(@PathVariable UUID id) {
        List<Comment> comments = commentInfoService.getAllByTaskId(id);
        if (comments.isEmpty()) {
            return ResponseEntity.ok().body("Нет комментариев на это задание");
        }
        return ResponseEntity.ok(comments);
    }

}
