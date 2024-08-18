package com.example.demo.services;

import com.example.demo.DTO.LeaveCommentRequest;
import com.example.demo.DTO.UpdateCommentRequest;
import com.example.demo.models.Comment;
import com.example.demo.repositories.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentManagementServiceImpl implements CommentManagementService {

    private CommentRepository commentRepository;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    private UserInfoService userInfoService;

    @Autowired
    public void setUserInfoService(UserInfoServiceImpl userInfoService) {
        this.userInfoService = userInfoService;
    }

    private TaskInfoService taskInfoService;

    @Autowired
    public void setTaskInfoService(TaskInfoServiceImpl taskInfoService) {
        this.taskInfoService = taskInfoService;
    }

    @Override
    public Comment update(UpdateCommentRequest request) {
        return commentRepository.findById(request.getCommentId())
                .map(existingComment -> {
                    existingComment.setText(request.getCommentText());
                    return commentRepository.save(existingComment);
                })
                .orElseThrow(() -> new EntityNotFoundException("Комментарий с ID " + request.getCommentId() + " не найден"));
    }

    @Override
    public Comment create(LeaveCommentRequest request) {
        Comment comment = Comment.builder()
                .text(request.getCommentText())
                .task(taskInfoService.getById(request.getTaskId())
                        .orElseThrow(() -> new EntityNotFoundException("Не найден пользователь с указанным ID")))
                .commenter(userInfoService.getCurrentUser()
                        .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Вы не авторизованы")))
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }
}
