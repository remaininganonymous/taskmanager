package com.example.demo.services;

import com.example.demo.DTO.LeaveCommentRequest;
import com.example.demo.DTO.UpdateCommentRequest;
import com.example.demo.models.Comment;
import java.util.UUID;

public interface CommentManagementService {

    public Comment update(UpdateCommentRequest request);

    public Comment create(LeaveCommentRequest request);

    public void delete(UUID id);

}
