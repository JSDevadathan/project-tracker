//package com.edstem.projecttracker.service;
//
//import com.edstem.projecttracker.contract.request.CommentRequest;
//import com.edstem.projecttracker.contract.response.CommentResponse;
//import com.edstem.projecttracker.model.Comment;
//import com.edstem.projecttracker.repository.CommentRepository;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class CommentServiceTest {
//
//    @Test
//    public void testCreateComment() {
//        CommentRepository commentRepository = mock(CommentRepository.class);
//        ModelMapper modelMapper = new ModelMapper();
//        CommentService commentService = new CommentService(commentRepository, modelMapper);
//
//        CommentRequest commentRequest = new CommentRequest();
//        commentRequest.setText("Test comment text");
//
//        Comment savedComment = Comment.builder().id(1L).text("Test comment text").build();
//        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);
//        CommentResponse commentResponse = commentService.createComment(commentRequest);
//        assertNotNull(commentResponse);
//        assertEquals(savedComment.getText(), commentResponse.getText());
//        verify(commentRepository, times(1)).save(any(Comment.class));
//    }
//
//    @Test
//    public void testViewAllComments() {
//        CommentRepository commentRepository = mock(CommentRepository.class);
//        ModelMapper modelMapper = new ModelMapper();
//        CommentService commentService = new CommentService(commentRepository, modelMapper);
//
//        List<Comment> mockComments = Arrays.asList(
//                Comment.builder().id(1L).text("Comment 1").build(),
//                Comment.builder().id(2L).text("Comment 2").build()
//        );
//
//        when(commentRepository.findAll()).thenReturn(mockComments);
//        List<CommentResponse> commentResponses = commentService.viewAllComments();
//        assertNotNull(commentResponses);
//        assertEquals(mockComments.size(), commentResponses.size());
//        verify(commentRepository, times(1)).findAll();
//        assertEquals("Comment 1", commentResponses.get(0).getText());
//        assertEquals("Comment 2", commentResponses.get(1).getText());
//    }
//}
//
//
//
