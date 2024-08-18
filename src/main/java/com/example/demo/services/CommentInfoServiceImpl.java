package com.example.demo.services;

import com.example.demo.models.Comment;
import com.example.demo.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentInfoServiceImpl implements CommentInfoService {

    private CommentRepository commentRepository;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Comment> getById(UUID id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getAllByTaskId(UUID id) {
        return commentRepository.findAllByTaskId(id);
    }

    @Override
    public List<Comment> getAllByCommenterId(UUID id) {
        return commentRepository.findAllByCommenterId(id);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
