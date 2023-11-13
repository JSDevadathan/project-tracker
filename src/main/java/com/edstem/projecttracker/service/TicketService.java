package com.edstem.projecttracker.service;

import com.edstem.projecttracker.contract.request.TicketRequest;
import com.edstem.projecttracker.contract.response.TicketResponse;
import com.edstem.projecttracker.expection.EntityNotFoundException;
import com.edstem.projecttracker.model.Category;
import com.edstem.projecttracker.model.Ticket;
import com.edstem.projecttracker.repository.CategoryRepository;
import com.edstem.projecttracker.repository.TicketRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public TicketResponse createTicket(TicketRequest ticketRequestDto) {
        Category category =
                categoryRepository
                        .findByName(ticketRequestDto.getName());
        Ticket newTicket = modelMapper.map(ticketRequestDto, Ticket.class);
        Ticket ticket =
                Ticket.builder()
                        .title(ticketRequestDto.getTitle())
                        .requirements(ticketRequestDto.getRequirements())
                        .acceptanceCriteria(ticketRequestDto.getAcceptanceCriteria())
                        .comments(ticketRequestDto.getComments())
                        .category(category)
                        .name(ticketRequestDto.getName())
                        .build();
        ticket = ticketRepository.save(ticket);
        return modelMapper.map(ticket, TicketResponse.class);
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
        Ticket ticket =
                ticketRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Ticket not found", +id));
        Category category =
                categoryRepository
                        .findByName(ticketRequestDto.getName());
        ticket =
                Ticket.builder()
                        .id(ticket.getId())
                        .title(ticketRequestDto.getTitle())
                        .requirements(ticketRequestDto.getRequirements())
                        .acceptanceCriteria(ticketRequestDto.getAcceptanceCriteria())
                        .comments(ticketRequestDto.getComments())
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
