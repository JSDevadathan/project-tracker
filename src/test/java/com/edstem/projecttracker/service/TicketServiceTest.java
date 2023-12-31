package com.edstem.projecttracker.service;

import com.edstem.projecttracker.contract.request.TicketRequest;
import com.edstem.projecttracker.contract.response.TicketResponse;
import com.edstem.projecttracker.expection.EntityNotFoundException;
import com.edstem.projecttracker.model.Category;
import com.edstem.projecttracker.model.Ticket;
import com.edstem.projecttracker.repository.CategoryRepository;
import com.edstem.projecttracker.repository.TicketRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class TicketServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TicketService ticketService;

    @Test
    void testCreateTicket() {
        Category category = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();

        Ticket ticket = Ticket.builder()
                .acceptanceCriteria("Acceptance Criteria")
                .category(category)
                .description("The characteristics of someone or something")
                .ticketId(1L)
                .title("Dr")
                .build();

        when(ticketRepository.save(Mockito.any())).thenReturn(ticket);

        Category category2 = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();
        Optional<Category> ofResult = Optional.of(category2);
        when(categoryRepository.findById(Mockito.any())).thenReturn(ofResult);

        TicketRequest ticketRequest = TicketRequest.builder()
                .acceptanceCriteria("Acceptance Criteria")
                .categoryId(1L)
                .description("The characteristics of someone or something")
                .title("Dr")
                .build();

        Category category3 = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();
        ticketService.createTicket(ticketRequest);
        verify(modelMapper).map(Mockito.any(), Mockito.any());
        verify(categoryRepository).findById(Mockito.any());
        verify(ticketRepository).save(Mockito.any());
    }


    @Test
    void testViewTicket() {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        ticketList.add(new Ticket());
        ticketList.add(new Ticket());
        when(ticketRepository.findAll()).thenReturn(ticketList);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any()))
                .thenReturn(TicketResponse.builder()
                        .acceptanceCriteria("The characteristics of someone or something")
                        .categoryId(1L)
                        .description("Description")
                        .title("Dr")
                        .build());
        List<TicketResponse> actualViewTicketResult = ticketService.viewTicket();
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any());
        verify(ticketRepository).findAll();
        assertEquals(2, actualViewTicketResult.size());
    }

    @Test
    void testGetTicketsByCategoryId() {
        when(ticketRepository.findByCategoryCategoryId(Mockito.any())).thenReturn(new ArrayList<>());

        Category category = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();

        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findById(Mockito.any())).thenReturn(ofResult);

        List<TicketResponse> actualTicketsByCategoryId = ticketService.getTicketsByCategoryId(1L);

        verify(ticketRepository).findByCategoryCategoryId(Mockito.any());
        assertTrue(actualTicketsByCategoryId.isEmpty());
    }


    @Test
    void testGetTicketsByCategoryName() {
        when(ticketRepository.findByCategory(Mockito.any())).thenReturn(new ArrayList<>());

        Category category = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();

        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findByName(Mockito.any())).thenReturn(ofResult);

        List<TicketResponse> actualTicketsByCategoryName = ticketService.getTicketsByCategoryName("Name");

        verify(categoryRepository).findByName(Mockito.any());
        verify(ticketRepository).findByCategory(Mockito.any());
        assertTrue(actualTicketsByCategoryName.isEmpty());
    }

    @Test
    void testUpdateTicket() {
        Category category = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();

        Ticket ticket = Ticket.builder()
                .acceptanceCriteria("Acceptance Criteria")
                .category(category)
                .description("The characteristics of someone or something")
                .ticketId(1L)
                .title("Dr")
                .build();

        Optional<Ticket> ofResult = Optional.of(ticket);

        Category category2 = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();

        Ticket ticket2 = Ticket.builder()
                .acceptanceCriteria("Acceptance Criteria")
                .category(category2)
                .description("The characteristics of someone or something")
                .ticketId(1L)
                .title("Dr")
                .build();

        when(ticketRepository.save(Mockito.any())).thenReturn(ticket2);
        when(ticketRepository.findById(Mockito.any())).thenReturn(ofResult);

        Category category3 = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();
        Optional<Category> ofResult2 = Optional.of(category3);
        when(categoryRepository.findById(Mockito.any())).thenReturn(ofResult2);

        when(modelMapper.map(Mockito.any(), Mockito.any()))
                .thenReturn(TicketResponse.builder()
                        .acceptanceCriteria("Acceptance Criteria")
                        .categoryId(1L)
                        .description("The characteristics of someone or something")
                        .ticketId(1L)
                        .title("Dr")
                        .build());

        Category category4 = Category.builder()
                .categoryId(1L)
                .name("Name")
                .build();

        TicketRequest ticketRequestDto = TicketRequest.builder()
                .build();

        ticketService.updateTicket(1L, ticketRequestDto);

        verify(modelMapper).map(Mockito.any(), Mockito.any());
        verify(categoryRepository).findById(Mockito.any());
        verify(ticketRepository).findById(Mockito.any());
        verify(ticketRepository).save(Mockito.any());
    }


    @Test
    void testDeleteTicket() {
        doNothing().when(ticketRepository).delete(Mockito.<Ticket>any());
        Optional<Ticket> ofResult = Optional.of(new Ticket());
        when(ticketRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ticketService.deleteTicket(1L);
        verify(ticketRepository).delete(Mockito.<Ticket>any());
        verify(ticketRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testSearchPagination2() {
        ArrayList<Ticket> content = new ArrayList<>();
        content.add(new Ticket());
        PageImpl<Ticket> pageImpl = new PageImpl<>(content);
        when(ticketRepository.searchPosts(Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        TicketResponse buildResult = TicketResponse.builder()
                .acceptanceCriteria("Acceptance Criteria")
                .categoryId(1L)
                .description("The characteristics of someone or something")
                .ticketId(1L)
                .title("Dr")
                .build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any())).thenReturn(buildResult);
        Page<TicketResponse> actualSearchPaginationResult = ticketService.SearchPagination("Query", null);
        verify(ticketRepository).searchPosts(Mockito.<String>any(), Mockito.<Pageable>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any());
        assertEquals(1, actualSearchPaginationResult.toList().size());
    }

    @Test
    public void testSearchPagination_throwsEntityNotFoundException() {

        String query = "test";
        Pageable pageable = mock(Pageable.class);
        Page<Ticket> page = mock(Page.class);

        when(ticketRepository.searchPosts(query, pageable)).thenReturn(page);
        when(page.isEmpty()).thenReturn(true);

        assertThrows(EntityNotFoundException.class, () -> ticketService.SearchPagination(query, pageable));
    }
}

