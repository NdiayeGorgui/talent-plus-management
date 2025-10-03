package com.gogo.candidat_service.dto;

import lombok.Data;

@Data
public class MetadonneeRHDTO {
    private String domaineRecherche;
    private String typeContrat;
    private String localisation;
    private String disponibilite;
    private Double pretentionsSalariales;
    private String source;
}

