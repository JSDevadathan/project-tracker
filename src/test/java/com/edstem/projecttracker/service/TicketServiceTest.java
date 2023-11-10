package com.edstem.projecttracker.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.projecttracker.contract.request.TicketRequest;
import com.edstem.projecttracker.contract.response.TicketResponse;
import com.edstem.projecttracker.expection.EntityNotFoundException;
import com.edstem.projecttracker.model.Category;
import com.edstem.projecttracker.model.Ticket;
import com.edstem.projecttracker.repository.CategoryRepository;
import com.edstem.projecttracker.repository.TicketRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TicketService.class})
@ExtendWith(SpringExtension.class)
class TicketServiceTest {
    @MockBean private CategoryRepository categoryRepository;

    @MockBean private ModelMapper modelMapper;

    @MockBean private TicketRepository ticketRepository;

    @Autowired private TicketService ticketService;

    @Test
    void testCreateTicket() {
        when(categoryRepository.findByName(Mockito.<String>any())).thenReturn(new Category());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
                .thenThrow(new EntityNotFoundException("Entity"));
        assertThrows(EntityNotFoundException.class, () -> ticketService.createTicket(
                new TicketRequest("Dr", "Requirements", "The characteristics of someone or something", "Comments", "Name")));
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Ticket>>any());
    }

    @Test
    void testViewTicket() {
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>());
        List<TicketResponse> actualViewTicketResult = ticketService.viewTicket();
        verify(ticketRepository).findAll();
        assertTrue(actualViewTicketResult.isEmpty());
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
                        .comments("Comments")
                        .description("The characteristics of someone or something")
                        .id(1L)
                        .name("Name")
                        .requirements("Requirements")
                        .title("Dr")
                        .build());
        ticketService.updateTicket(1L,
                new TicketRequest("Dr", "Requirements", "The characteristics of someone or something", "Comments", "Name"));
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
