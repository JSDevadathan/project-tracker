package com.edstem.projecttracker.service;

import com.edstem.projecttracker.contract.request.TicketRequest;
import com.edstem.projecttracker.contract.response.TicketResponse;
import com.edstem.projecttracker.expection.EntityNotFoundException;
import com.edstem.projecttracker.model.Category;
import com.edstem.projecttracker.model.Ticket;
import com.edstem.projecttracker.repository.CategoryRepository;
import com.edstem.projecttracker.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public TicketResponse createTicket(TicketRequest ticketRequestDto) {
       Category category =
                categoryRepository.findById(ticketRequestDto.getCategory().getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        Ticket ticket = Ticket.builder()
                .title(ticketRequestDto.getTitle())
                .description(ticketRequestDto.getDescription())
                .acceptanceCriteria(ticketRequestDto.getAcceptanceCriteria())
                .category(category)
                .build();
        ticket = ticketRepository.save(ticket);
        return convertToDto(ticket);
    }

    public TicketResponse convertToDto(Ticket ticket) {
        return modelMapper.map(ticket, TicketResponse.class);
    }


    public List<TicketResponse> viewTicket() {
        List<Ticket> userProfiles = (List<Ticket>) ticketRepository.findAll();
        return userProfiles.stream()
                .map(user -> modelMapper.map(user, TicketResponse.class))
                .collect(Collectors.toList());
    }

    public List<TicketResponse> getTicketsByCategoryId(Long categoryId) {
        Category category =
                categoryRepository
                        .findById(categoryId)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Category not found", +categoryId));
        List<Ticket> tickets = category.getTickets();
        return tickets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public TicketResponse updateTicket(Long id, TicketRequest ticketRequestDto) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
        Category category = categoryRepository.findById(ticketRequestDto.getCategory().getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        ticket = Ticket.builder()
                .ticketId(ticket.getTicketId())
                .title(ticketRequestDto.getTitle())
                .description(ticketRequestDto.getDescription())
                .acceptanceCriteria(ticketRequestDto.getAcceptanceCriteria())
                .category(category)
                .build();
        ticket = ticketRepository.save(ticket);
        return convertToDto(ticket);
    }

    public List<TicketResponse> getTicketsByCategoryName(String name) {
        Category category = categoryRepository.findByName(name);
        List<Ticket> tickets = category.getTickets();
        return tickets.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void deleteTicket(Long id) {
        Ticket ticket =
                ticketRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Ticket not found", +id));
        ticketRepository.delete(ticket);
    }
}
