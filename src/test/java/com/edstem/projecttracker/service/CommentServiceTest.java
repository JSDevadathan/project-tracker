package com.edstem.projecttracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.projecttracker.contract.request.CommentRequest;
import com.edstem.projecttracker.contract.response.CommentResponse;
import com.edstem.projecttracker.model.Category;
import com.edstem.projecttracker.model.Comment;
import com.edstem.projecttracker.model.Ticket;
import com.edstem.projecttracker.repository.CommentRepository;
import com.edstem.projecttracker.repository.TicketRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        when(commentRepository.save(Mockito.<Comment>any())).thenReturn(new Comment());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CommentResponse>>any()))
                .thenReturn(CommentResponse.builder().commentId(1L).text("Text").ticketId(1L).build());

        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("Name");
        category.setTickets(new ArrayList<>());

        Ticket ticket = new Ticket();
        ticket.setAcceptanceCriteria("Acceptance Criteria");
        ticket.setCategory(category);
        ticket.setComments(new ArrayList<>());
        ticket.setDescription("The characteristics of someone or something");
        ticket.setTicketId(1L);
        ticket.setTitle("Dr");
        Optional<Ticket> ofResult = Optional.of(ticket);
        when(ticketRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        commentService.createComment(new CommentRequest());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<CommentResponse>>any());
        verify(ticketRepository).findById(Mockito.<Long>any());
        verify(commentRepository).save(Mockito.<Comment>any());
    }

    @Test
    void testViewAllComments() {
        ArrayList<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment());
        commentList.add(new Comment());
        when(commentRepository.findAll()).thenReturn(commentList);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CommentResponse>>any()))
                .thenReturn(CommentResponse.builder().commentId(1L).text("Text").ticketId(1L).build());
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

