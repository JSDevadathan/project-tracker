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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
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
        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("Name");

        Ticket ticket = new Ticket();
        ticket.setAcceptanceCriteria("Acceptance Criteria");
        ticket.setCategory(category);
        ticket.setComments(new ArrayList<>());
        ticket.setDescription("The characteristics of someone or something");
        ticket.setTicketId(1L);
        ticket.setTitle("Dr");
        when(ticketRepository.save(Mockito.<Ticket>any())).thenReturn(ticket);

        Category category2 = new Category();
        category2.setCategoryId(1L);
        category2.setName("Name");
        Optional<Category> ofResult = Optional.of(category2);
        when(categoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any()))
                .thenReturn(TicketResponse.builder()
                        .acceptanceCriteria("Acceptance Criteria")
                        .categoryId(1L)
                        .description("The characteristics of someone or something")
                        .ticketId(1L)
                        .title("Dr")
                        .build());

        Category category3 = new Category();
        category3.setCategoryId(1L);
        category3.setName("Name");
        ticketService.createTicket(TicketRequest.builder()
                .acceptanceCriteria("Acceptance Criteria")
                .categoryId(1L)
                .description("The characteristics of someone or something")
                .title("Dr")
                .build());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any());
        verify(categoryRepository).findById(Mockito.<Long>any());
        verify(ticketRepository).save(Mockito.<Ticket>any());
    }


    @Test
    void testConvertToDto() {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any()))
                .thenReturn(TicketResponse.builder()
                        .acceptanceCriteria("The characteristics of someone or something")
                        .categoryId(1L)
                        .description("Description")
                        .title("Dr")
                        .build());
        ticketService.convertToDto(new Ticket());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any());
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
        when(ticketRepository.findByCategory_CategoryId(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("Name");
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        List<TicketResponse> actualTicketsByCategoryId = ticketService.getTicketsByCategoryId(1L);
        verify(ticketRepository).findByCategory_CategoryId(Mockito.<Long>any());
        verify(categoryRepository).findById(Mockito.<Long>any());
        assertTrue(actualTicketsByCategoryId.isEmpty());
    }

    @Test
    void testGetTicketsByCategoryIdCategoryNotFound() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> ticketService.getTicketsByCategoryId(categoryId));
    }


    @Test
    void testGetTicketsByCategoryName() {
        when(ticketRepository.findByCategory(Mockito.<Category>any())).thenReturn(new ArrayList<>());

        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("Name");
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);
        List<TicketResponse> actualTicketsByCategoryName = ticketService.getTicketsByCategoryName("Name");
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(ticketRepository).findByCategory(Mockito.<Category>any());
        assertTrue(actualTicketsByCategoryName.isEmpty());
    }

    @Test
    public void testGetTicketsByCategoryNameThrowsEntityNotFoundException() {
        // Arrange
        String categoryName = "NonExistentCategory";
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

        // Assert
        assertThrows(EntityNotFoundException.class, () -> {
            // Act
            ticketService.getTicketsByCategoryName(categoryName);
        });
    }

    @Test
    void testUpdateTicket() {
        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("Name");

        Ticket ticket = new Ticket();
        ticket.setAcceptanceCriteria("Acceptance Criteria");
        ticket.setCategory(category);
        ticket.setComments(new ArrayList<>());
        ticket.setDescription("The characteristics of someone or something");
        ticket.setTicketId(1L);
        ticket.setTitle("Dr");
        Optional<Ticket> ofResult = Optional.of(ticket);

        Category category2 = new Category();
        category2.setCategoryId(1L);
        category2.setName("Name");

        Ticket ticket2 = new Ticket();
        ticket2.setAcceptanceCriteria("Acceptance Criteria");
        ticket2.setCategory(category2);
        ticket2.setComments(new ArrayList<>());
        ticket2.setDescription("The characteristics of someone or something");
        ticket2.setTicketId(1L);
        ticket2.setTitle("Dr");
        when(ticketRepository.save(Mockito.<Ticket>any())).thenReturn(ticket2);
        when(ticketRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Category category3 = new Category();
        category3.setCategoryId(1L);
        category3.setName("Name");
        Optional<Category> ofResult2 = Optional.of(category3);
        when(categoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any()))
                .thenReturn(TicketResponse.builder()
                        .acceptanceCriteria("Acceptance Criteria")
                        .categoryId(1L)
                        .description("The characteristics of someone or something")
                        .ticketId(1L)
                        .title("Dr")
                        .build());

        Category category4 = new Category();
        category4.setCategoryId(1L);
        category4.setName("Name");

        TicketRequest ticketRequestDto = new TicketRequest();
        ticketService.updateTicket(1L, ticketRequestDto);
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any());
        verify(categoryRepository).findById(Mockito.<Long>any());
        verify(ticketRepository).findById(Mockito.<Long>any());
        verify(ticketRepository).save(Mockito.<Ticket>any());
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
    void testSearchPosts() {
        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("Name");

        Ticket ticket = new Ticket();
        ticket.setAcceptanceCriteria("Acceptance Criteria");
        ticket.setCategory(category);
        ticket.setComments(new ArrayList<>());
        ticket.setDescription("The characteristics of someone or something");
        ticket.setTicketId(1L);
        ticket.setTitle("Dr");

        ArrayList<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);
        when(ticketRepository.searchPosts(Mockito.<String>any())).thenReturn(ticketList);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any()))
                .thenThrow(new EntityNotFoundException("Entity"));
        assertThrows(EntityNotFoundException.class, () -> ticketService.searchPosts("Query"));
        verify(ticketRepository).searchPosts(Mockito.<String>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any());
    }

    @Test
    public void testSearchPostsThrowsEntityNotFoundException() {
        // Arrange
        String query = "NonExistentQuery";
        when(ticketRepository.searchPosts(anyString())).thenReturn(Collections.emptyList());

        // Assert
        assertThrows(EntityNotFoundException.class, () -> {
            // Act
            ticketService.searchPosts(query);
        });
    }

    @Test
    void testGetAppListByPageable() {
        when(ticketRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        Page<TicketResponse> actualAppListByPageable = ticketService.getAppListByPageable(null);
        verify(ticketRepository).findAll(Mockito.<Pageable>any());
        assertTrue(actualAppListByPageable.toList().isEmpty());
    }
}

