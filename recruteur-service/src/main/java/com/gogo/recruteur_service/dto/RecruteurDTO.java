package com.gogo.recruteur_service.dto;

import com.gogo.recruteur_service.enums.Niveau;
import lombok.Data;

@Data
public class RecruteurDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String poste;
    private Niveau niveau;
}

