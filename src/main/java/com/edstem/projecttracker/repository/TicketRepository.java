package com.edstem.projecttracker.repository;

import com.edstem.projecttracker.model.Ticket;
import org.springdoc.core.converters.models.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT p FROM Ticket p WHERE " +
            "p.title LIKE CONCAT('%',:query, '%') " +
            "OR p.description LIKE CONCAT('%', :query, '%') " +
            "OR p.acceptanceCriteria LIKE CONCAT('%', :query, '%') " +
            "OR p.category.name LIKE CONCAT('%', :query, '%')")
    List<Ticket> searchPosts(String query);
}
