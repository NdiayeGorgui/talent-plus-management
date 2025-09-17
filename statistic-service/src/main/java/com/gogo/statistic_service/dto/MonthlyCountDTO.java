package com.gogo.statistic_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyCountDTO {
    private String mois;  // ex: "2025-09"
    private Long count;
}
