package com.gogo.candidat_service.dto;

import lombok.Data;

@Data
public class CompetenceLinguistiqueDTO {
    private Long id;
    // Langue de la compétence (ex : "fr, en")
    private String langue;
    // niveau doit correspondre à ton enum Niveau (DEBUTANT, INTERMEDIAIRE, EXPERT)
    private String niveau;
}


