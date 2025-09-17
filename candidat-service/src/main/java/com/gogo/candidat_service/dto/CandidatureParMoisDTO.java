package com.gogo.candidat_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidatureParMoisDTO {
    private String mois;   // exemple "Janvier"
    private Long total;    // nombre de candidatures
}

