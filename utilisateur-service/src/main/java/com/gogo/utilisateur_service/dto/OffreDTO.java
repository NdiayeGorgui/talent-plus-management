package com.gogo.utilisateur_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OffreDTO {

    private Long id;
    private String titre;
    private String description;
    private LocalDateTime datePublication;
    private LocalDateTime dateFinAffichage;
    private String categorie;
    private String ville;
    private String pays;
    private boolean active;
    private Long recruteurId;

}

