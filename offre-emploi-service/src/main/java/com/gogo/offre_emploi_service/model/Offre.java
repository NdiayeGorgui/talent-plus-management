package com.gogo.offre_emploi_service.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "offres")
public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(length = 2000)
    private String description;

    private LocalDateTime datePublication = LocalDateTime.now();
    private String categorie;
    private String ville;
    private String pays;
    private boolean active = true; // pour clôturer l'offre
    // association via id seulement (microservice Recruteur séparé)
    private Long recruteurId;

}

