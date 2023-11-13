package com.edstem.projecttracker.controller;

import com.edstem.projecttracker.contract.request.CommentRequest;
import com.edstem.projecttracker.contract.response.CommentResponse;
import com.edstem.projecttracker.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

//    @Test
//    public void testCreateComment() throws Exception {
//        CommentRequest commentRequest = new CommentRequest();
//        CommentResponse commentResponse = new CommentResponse();
//        when(commentService.createComment(commentRequest)).thenReturn(commentResponse);
//        mockMvc.perform(post("/comments")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(commentRequest)))
//                .andExpect(status().isOk());
//    }

    @Test
    public void testViewAllComments() throws Exception {
        CommentResponse commentResponse = new CommentResponse();
        when(commentService.viewAllComments()).thenReturn(Arrays.asList(commentResponse));
        mockMvc.perform(get("/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
