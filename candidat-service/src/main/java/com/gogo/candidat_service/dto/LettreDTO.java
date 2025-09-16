package com.gogo.candidat_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LettreDTO {
    private Long id;
    private String titre;
    private String contenu; // texte de la lettre si tu veux stocker le texte en DB
    private String fichierUrl; // facultatif : url si tu as uploadé le fichier et veux stocker le lien
    private Integer version;
    private LocalDateTime dateDepot;
}

