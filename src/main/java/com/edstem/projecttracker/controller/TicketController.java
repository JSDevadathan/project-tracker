package com.edstem.projecttracker.controller;

import com.edstem.projecttracker.contract.request.TicketRequest;
import com.edstem.projecttracker.contract.response.TicketResponse;
import com.edstem.projecttracker.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/tickets")
@CrossOrigin("*")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/create")
    public TicketResponse createTicket(@RequestBody TicketRequest ticketRequestDto) {
        return ticketService.createTicket(ticketRequestDto);
    }

    @GetMapping("/view")
    public List<TicketResponse> viewTicket() {
        return ticketService.viewTicket();
    }

    @GetMapping("/categories/{categoryId}")
    public List<TicketResponse> getTicketsByCategoryId(@PathVariable Long categoryId) {
        return ticketService.getTicketsByCategoryId(categoryId);
    }

    @PutMapping("/{id}")
    public TicketResponse updateTicket(
            @PathVariable Long id, @RequestBody TicketRequest ticketRequestDto) {
        return ticketService.updateTicket(id, ticketRequestDto);
    }

    @GetMapping("/categories/name/{name}")
    public List<TicketResponse> getTicketsByCategoryName(@PathVariable String name) {
        return ticketService.getTicketsByCategoryName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }

    @GetMapping("/search")
    public List<TicketResponse> searchPosts(@RequestParam("query") String query) {
        return ticketService.searchPosts(query);
    }

    @GetMapping("/pageable")
    public Page<TicketResponse> getPostsByPageable(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ticketService.getAppListByPageable(pageable);
    }
}
