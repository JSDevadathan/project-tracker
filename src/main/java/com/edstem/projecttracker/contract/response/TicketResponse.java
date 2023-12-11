package com.edstem.projecttracker.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {
    private Long ticketId;
    private String title;
    private String description;
    private String acceptanceCriteria;
    private Long categoryId;
}
