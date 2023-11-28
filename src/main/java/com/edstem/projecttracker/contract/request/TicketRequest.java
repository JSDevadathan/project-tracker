package com.edstem.projecttracker.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketRequest {
    private String title;
    private String description;
    private String acceptanceCriteria;
    private Long categoryId;
}
