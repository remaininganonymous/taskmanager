package com.example.demo.services;

import com.example.demo.models.Comment;
import com.example.demo.models.Task;
import com.example.demo.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentInfoService {

    public Optional<Comment> getById(UUID id);

    List<Comment> getAllByTaskId(UUID id);

    public List<Comment> getAllByCommenterId(UUID id);

    public List<Comment> getAllComments();

}
