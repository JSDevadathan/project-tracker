package com.edstem.projecttracker.service;

import com.edstem.projecttracker.contract.request.CommentRequest;
import com.edstem.projecttracker.contract.response.CommentResponse;
import com.edstem.projecttracker.model.Category;
import com.edstem.projecttracker.model.Comment;
import com.edstem.projecttracker.model.Ticket;
import com.edstem.projecttracker.repository.CommentRepository;
import com.edstem.projecttracker.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CommentServiceTest {
    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private TicketRepository ticketRepository;

    @Test
    void testCreateComment() {
        when(commentRepository.save(Mockito.any())).thenReturn(new Comment());
        when(modelMapper.map(Mockito.any(), Mockito.any()))
                .thenReturn(CommentResponse.builder().commentId(1L).text("Text").build());

        Category category = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();

        Ticket ticket = Ticket.builder()
                .acceptanceCriteria("Acceptance Criteria")
                .category(category)
                .comments(new ArrayList<>())
                .description("The characteristics of someone or something")
                .ticketId(1L)
                .title("Dr")
                .build();

        Optional<Ticket> ofResult = Optional.of(ticket);
        when(ticketRepository.findById(Mockito.any())).thenReturn(ofResult);

        CommentRequest commentRequest = CommentRequest.builder()
                .build();

        commentService.createComment(commentRequest);

        verify(modelMapper).map(Mockito.any(), Mockito.any());
        verify(ticketRepository).findById(Mockito.any());
        verify(commentRepository).save(Mockito.any());
    }


    @Test
    void testViewAllComments() {
        ArrayList<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment());
        commentList.add(new Comment());
        when(commentRepository.findAll()).thenReturn(commentList);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CommentResponse>>any()))
                .thenReturn(CommentResponse.builder().commentId(1L).text("Text").build());
        List<CommentResponse> actualViewAllCommentsResult = commentService.viewAllComments();
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<CommentResponse>>any());
        verify(commentRepository).findAll();
        assertEquals(2, actualViewAllCommentsResult.size());
    }

    @Test
    void testDeleteComment() {
        doNothing().when(commentRepository).delete(Mockito.<Comment>any());
        Optional<Comment> ofResult = Optional.of(new Comment());
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        commentService.deleteComment(1L);
        verify(commentRepository).delete(Mockito.<Comment>any());
        verify(commentRepository).findById(Mockito.<Long>any());
    }
}

