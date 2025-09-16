package com.gogo.candidat_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExperienceDTO {
    private Long id;
    private String poste;
    private String entreprise;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String description;
}

