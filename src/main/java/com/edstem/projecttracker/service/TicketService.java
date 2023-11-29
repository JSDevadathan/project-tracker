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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public TicketResponse createTicket(TicketRequest ticketRequest) {
        Category category =
                categoryRepository.findById(ticketRequest.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        Ticket ticket = Ticket.builder()
                .title(ticketRequest.getTitle())
                .description(ticketRequest.getDescription())
                .acceptanceCriteria(ticketRequest.getAcceptanceCriteria())
                .category(category)
                .build();
        ticket = ticketRepository.save(ticket);
        return modelMapper.map(ticket, TicketResponse.class);
    }

    public List<TicketResponse> viewTicket() {
        List<Ticket> userProfiles = ticketRepository.findAll();
        return userProfiles.stream()
                .map(user -> modelMapper.map(user, TicketResponse.class))
                .collect(Collectors.toList());
    }

    public List<TicketResponse> getTicketsByCategoryId(Long categoryId) {
        List<Ticket> tickets = ticketRepository.findByCategoryCategoryId(categoryId);

        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketResponse.class))
                .collect(Collectors.toList());
    }

    public TicketResponse updateTicket(Long id, TicketRequest ticketRequest) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        Category category = categoryRepository.findById(ticketRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        ticket = Ticket.builder()
                .ticketId(ticket.getTicketId())
                .title(ticketRequest.getTitle())
                .description(ticketRequest.getDescription())
                .acceptanceCriteria(ticketRequest.getAcceptanceCriteria())
                .category(category)
                .build();
        ticket = ticketRepository.save(ticket);
        return modelMapper.map(ticket, TicketResponse.class);
    }

    public List<TicketResponse> getTicketsByCategoryName(String name) {
        Category category = categoryRepository.findByName(name).orElseThrow();
        List<Ticket> tickets = ticketRepository.findByCategory(category);
        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketResponse.class))
                .collect(Collectors.toList());
    }

    public void deleteTicket(Long id) {
        Ticket ticket =
                ticketRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Ticket not found", +id));
        ticketRepository.delete(ticket);
    }

    public Page<TicketResponse> SearchPagination(String query, Pageable pageable) {
        Page<Ticket> tickets = ticketRepository.searchPosts(query, pageable);

        if (tickets.isEmpty()) {
            throw new EntityNotFoundException("Ticket found for the given query: " + query);
        }

        return tickets.map(ticket -> modelMapper.map(ticket, TicketResponse.class));
    }
}