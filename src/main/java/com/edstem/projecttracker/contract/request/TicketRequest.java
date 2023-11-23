package com.edstem.projecttracker.contract.request;

import com.edstem.projecttracker.model.Category;
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
    private String description;
    private String acceptanceCriteria;
    private Long categoryId;
}
