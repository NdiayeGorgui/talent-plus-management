package com.gogo.utilisateur_service.dto;

import lombok.Data;

@Data
public class MetadonneeRHDTO {
    private Long id;
    private String domaineRecherche;
    private String typeContrat;
    private String localisation;
    private String disponibilite;
    private Double pretentionsSalariales;
    private String source;
}

