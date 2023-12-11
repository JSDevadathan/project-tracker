package com.edstem.projecttracker.controller;

import com.edstem.projecttracker.contract.request.TicketRequest;
import com.edstem.projecttracker.contract.response.TicketResponse;
import com.edstem.projecttracker.model.Category;
import com.edstem.projecttracker.model.Ticket;
import com.edstem.projecttracker.repository.CategoryRepository;
import com.edstem.projecttracker.repository.TicketRepository;
import com.edstem.projecttracker.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTest {
    @Autowired
    private TicketController ticketController;

    @MockBean
    private TicketService ticketService;

    @Test
    void testCreateTicket() throws Exception {
        when(ticketService.createTicket(Mockito.<TicketRequest>any())).thenReturn(TicketResponse.builder()
                .acceptanceCriteria("Acceptance Criteria")
                .categoryId(1L)
                .description("The characteristics of someone or something")
                .ticketId(1L)
                .title("Dr")
                .build());

        Category category = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();

        TicketRequest ticketRequest = TicketRequest.builder()
                .acceptanceCriteria("Acceptance Criteria")
                .categoryId(1L)
                .description("The characteristics of someone or something")
                .title("Dr")
                .build();

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

        Category category = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();

        TicketRequest ticketRequest = TicketRequest.builder()
                .acceptanceCriteria("Acceptance Criteria")
                .categoryId(1L)
                .description("The characteristics of someone or something")
                .title("Dr")
                .build();

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

    @Test
    void testSearchPagination() {
        ArrayList<Ticket> content = new ArrayList<>();
        content.add(new Ticket());
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.searchPosts(Mockito.<String>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(content));
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        Page<TicketResponse> actualSearchPaginationResult = (new TicketController(
                new TicketService(ticketRepository, categoryRepository, new ModelMapper()))).SearchPagination("Query", null);
        verify(ticketRepository).searchPosts(Mockito.<String>any(), Mockito.<Pageable>any());
        assertEquals(1, actualSearchPaginationResult.toList().size());
    }
}
