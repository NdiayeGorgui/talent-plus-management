package com.gogo.utilisateur_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CandidatProfileDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDate dateNaissance;
    private String adresse;
    private String niveauEtude;
    private String role;


    // Pour les candidats
    private List<CompetenceDTO> competences;
    private List<CompetenceLinguistiqueDTO> competenceLinguistiques;
    private List<ExperienceDTO> experiences;
    private List<LettreDTO> lettres;
    private MetadonneeRHDTO metadonneeRH;
    private List<CvDTO> cvs;

}
