package com.gogo.candidat_service.dto;

import com.gogo.candidat_service.enums.Niveau;
import lombok.Data;

@Data
public class CompetenceDTO {
    private Long id;
    // libellé de la compétence (ex : "Java")
    private String libelle;
    // niveau doit correspondre à ton enum Niveau (DEBUTANT, INTERMEDIAIRE, EXPERT)
    private Niveau niveau;
}


