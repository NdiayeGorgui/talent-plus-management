package com.gogo.candidat_service.dto;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CandidatResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDate dateNaissance;
    private String adresse;
    private String disponibilite;

    private List<CvDTO> cvs;
    private List<LettreDTO> lettres;
    private List<ExperienceDTO> experiences;
    private List<CompetenceDTO> competences;
}

