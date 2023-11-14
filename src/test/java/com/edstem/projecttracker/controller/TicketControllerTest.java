package com.edstem.projecttracker.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.edstem.projecttracker.contract.request.TicketRequest;
import com.edstem.projecttracker.contract.response.TicketResponse;
import com.edstem.projecttracker.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTest {
    @Autowired private TicketController ticketController;

    @MockBean private TicketService ticketService;

    @Test
    void testCreateTicket() throws Exception {
        when(ticketService.createTicket(Mockito.<TicketRequest>any())).thenReturn(TicketResponse.builder()
                .acceptanceCriteria("Acceptance Criteria")
                .categoryId(1L)
                .description("The characteristics of someone or something")
                .ticketId(1L)
                .title("Dr")
                .build());

        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setAcceptanceCriteria("Acceptance Criteria");
        ticketRequest.setCategoryId(1L);
        ticketRequest.setDescription("The characteristics of someone or something");
        ticketRequest.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(ticketRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/tickets/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"ticketId\":1,\"title\":\"Dr\",\"description\":\"The characteristics of someone or something\",\"acceptanceCriteria"
                                        + "\":\"Acceptance Criteria\",\"categoryId\":1}"));
    }



    @Test
    void testViewTicket() throws Exception {
        when(ticketService.viewTicket()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/view");
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetTicketsByCategoryId() throws Exception {
        when(ticketService.getTicketsByCategoryId(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/tickets/categories/{categoryId}", 1L);
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }



    @Test
    void testGetTicketsByCategoryName() throws Exception {
        when(ticketService.getTicketsByCategoryName(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/categories/name/{name}",
                "Name");
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdateTicket() throws Exception {
        when(ticketService.updateTicket(Mockito.<Long>any(), Mockito.<TicketRequest>any()))
                .thenReturn(TicketResponse.builder()
                        .acceptanceCriteria("Acceptance Criteria")
                        .categoryId(1L)
                        .description("The characteristics of someone or something")
                        .ticketId(1L)
                        .title("Dr")
                        .build());

        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setAcceptanceCriteria("Acceptance Criteria");
        ticketRequest.setCategoryId(1L);
        ticketRequest.setDescription("The characteristics of someone or something");
        ticketRequest.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(ticketRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/tickets/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"ticketId\":1,\"title\":\"Dr\",\"description\":\"The characteristics of someone or something\",\"acceptanceCriteria"
                                        + "\":\"Acceptance Criteria\",\"categoryId\":1}"));
    }

    @Test
    void testDeleteTicket() throws Exception {
        doNothing().when(ticketService).deleteTicket(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/tickets/{id}", 1L);
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
