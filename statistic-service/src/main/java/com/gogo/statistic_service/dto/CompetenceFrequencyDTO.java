package com.gogo.statistic_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetenceFrequencyDTO {
    private String competence;
    private Long frequency;
}
