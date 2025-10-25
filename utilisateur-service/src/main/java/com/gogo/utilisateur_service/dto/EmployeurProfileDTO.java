package com.gogo.utilisateur_service.dto;

import com.gogo.utilisateur_service.enums.Niveau;
import lombok.Data;

import java.util.List;

@Data
public class EmployeurProfileDTO {

    private Long id;
    private String nom;
    private String emailContact;
    private String telephone;
    private String poste;
    private String role;

    private List<OffreDTO> offres;
}

