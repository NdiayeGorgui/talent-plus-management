package com.gogo.candidat_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostulerRequest {

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDate dateNaissance;
    private String adresse;
    private String disponibilite;

    private List<ExperienceDTO> experiences;
    private List<CompetenceDTO> competences;
    private List<LettreDTO> lettres;
    private List<CvDTO> cvs;
}

