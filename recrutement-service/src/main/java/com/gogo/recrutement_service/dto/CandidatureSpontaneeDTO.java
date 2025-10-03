package com.gogo.recrutement_service.dto;

import lombok.Data;

@Data
public class CandidatureSpontaneeDTO {
    private Long candidatId;
    private String messageMotivation; // facultatif, ex: pourquoi il postule spontanément
}
