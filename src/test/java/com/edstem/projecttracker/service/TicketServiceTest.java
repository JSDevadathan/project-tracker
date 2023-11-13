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
        when(categoryRepository.findByName(Mockito.<String>any())).thenReturn(new Category());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(new Ticket());
        TicketRequest ticketRequestDto = mock(TicketRequest.class);
        when(ticketRequestDto.getTitle()).thenThrow(new EntityNotFoundException("Entity"));
        when(ticketRequestDto.getName()).thenReturn("Name");
        assertThrows(EntityNotFoundException.class, () -> ticketService.createTicket(ticketRequestDto));
        verify(ticketRequestDto).getName();
        verify(ticketRequestDto).getTitle();
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
    }

    @Test
    void testConvertToDto() {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any()))
                .thenReturn(TicketResponse.builder()
                        .acceptanceCriteria("The characteristics of someone or something")
                        .id(1L)
                        .name("Name")
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
                        .id(1L)
                        .name("Name")
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
        Category.CategoryBuilder nameResult = Category.builder().categoryId(1L).name("Name");
        Optional<Category> ofResult = Optional.of(nameResult.tickets(new ArrayList<>()).build());
        when(categoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        List<TicketResponse> actualTicketsByCategoryId = ticketService.getTicketsByCategoryId(1L);
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
        Category.CategoryBuilder nameResult = Category.builder().categoryId(1L).name("Name");
        when(categoryRepository.findByName(Mockito.<String>any()))
                .thenReturn(nameResult.tickets(new ArrayList<>()).build());
        List<TicketResponse> actualTicketsByCategoryName = ticketService.getTicketsByCategoryName("Name");
        verify(categoryRepository).findByName(Mockito.<String>any());
        assertTrue(actualTicketsByCategoryName.isEmpty());
    }

    @Test
    void testUpdateTicket() {
        when(ticketRepository.save(Mockito.<Ticket>any())).thenReturn(new Ticket());
        Optional<Ticket> ofResult = Optional.of(new Ticket());
        when(ticketRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(categoryRepository.findByName(Mockito.<String>any())).thenReturn(new Category());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any()))
                .thenReturn(TicketResponse.builder()
                        .acceptanceCriteria("The characteristics of someone or something")
                        .id(1L)
                        .name("Name")
                        .description("Description")
                        .title("Dr")
                        .build());
        ticketService.updateTicket(1L,
                new TicketRequest("Dr", "Requirements", "The characteristics of someone or something", "Name"));
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<TicketResponse>>any());
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
}

