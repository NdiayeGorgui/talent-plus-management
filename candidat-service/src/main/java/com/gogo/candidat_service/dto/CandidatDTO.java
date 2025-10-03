package com.gogo.candidat_service.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CandidatDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDate dateNaissance;
    private String adresse;

}

