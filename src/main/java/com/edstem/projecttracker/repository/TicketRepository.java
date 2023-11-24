package com.edstem.projecttracker.repository;

import com.edstem.projecttracker.model.Category;
import com.edstem.projecttracker.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT p FROM Ticket p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%',:query, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.acceptanceCriteria) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR p.category.name LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Ticket> searchPosts(String query);

    List<Ticket> findByCategoryCategoryId(Long categoryId);

    List<Ticket> findByCategory(Category category);
}
