package com.gogo.employeur_service.dto;

import com.gogo.employeur_service.enums.TypeEntreprise;
import lombok.Data;
@Data
public class EmployeurDTO {
    private Long id;
    private String nom;
    private TypeEntreprise typeEntreprise;
    private String emailContact;
    private String telephone;
    private String poste;
    private String adresse;
    private String ville;
    private String pays;
}

