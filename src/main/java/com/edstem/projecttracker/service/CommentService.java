package com.edstem.projecttracker.service;

import com.edstem.projecttracker.contract.request.CommentRequest;
import com.edstem.projecttracker.contract.response.CommentResponse;
import com.edstem.projecttracker.expection.EntityNotFoundException;
import com.edstem.projecttracker.model.Comment;
import com.edstem.projecttracker.model.Ticket;
import com.edstem.projecttracker.repository.CommentRepository;
import com.edstem.projecttracker.repository.TicketRepository;
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
    private final TicketRepository ticketRepository;

    public CommentResponse createComment(CommentRequest commentRequest) {
        Ticket ticket = ticketRepository.findById(commentRequest.getTicketId()).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        Comment comment = Comment.builder()
                .text(commentRequest.getText())
                .ticket(ticket)
                .build();
        comment = commentRepository.save(comment);
        return convertToDto(comment);
    }

    private CommentResponse convertToDto(Comment comment) {
        return modelMapper.map(comment, CommentResponse.class);
    }

    public List<CommentResponse> viewAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentResponse.class))
                .collect(Collectors.toList());
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment Not Found"));
        commentRepository.delete(comment);
    }
}
