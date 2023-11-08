package com.edstem.projecttracker.contract.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketRequest {
    private String title;

    @Column(columnDefinition = "text")
    private String requirements;

    @Column(columnDefinition = "text")
    private String description;
    private String comments;
    private Long categoryId;
}
