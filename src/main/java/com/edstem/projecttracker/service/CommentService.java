package com.edstem.projecttracker.service;

import com.edstem.projecttracker.contract.request.CommentRequest;
import com.edstem.projecttracker.contract.response.CommentResponse;
import com.edstem.projecttracker.model.Comment;
import com.edstem.projecttracker.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentResponse createComment(CommentRequest commentRequest) {
        Comment newComment = modelMapper.map(commentRequest, Comment.class);
        Comment comment =
                Comment.builder()
                        .text(commentRequest.getText())
                        .build();
        comment = commentRepository.save(comment);
        return modelMapper.map(comment, CommentResponse.class);
    }

    public List<CommentResponse> viewAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentResponse.class))
                .collect(Collectors.toList());
    }

}
