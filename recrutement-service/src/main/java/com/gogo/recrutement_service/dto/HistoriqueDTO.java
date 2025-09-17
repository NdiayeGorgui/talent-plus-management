package com.gogo.recrutement_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoriqueDTO {
    private Long id;
    private Long processusId;
    private String statut;
    private LocalDateTime dateChangement;
    private String recruteur; // login ou id du recruteur qui a modifié

}

