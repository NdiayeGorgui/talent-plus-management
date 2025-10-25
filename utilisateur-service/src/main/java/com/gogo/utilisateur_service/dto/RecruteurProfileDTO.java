package com.gogo.utilisateur_service.dto;

import com.gogo.utilisateur_service.enums.Niveau;
import lombok.Data;

import java.util.List;

@Data
public class RecruteurProfileDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String poste;
    private Niveau niveau;
    private String role;

    private List<OffreDTO> offres;
}

