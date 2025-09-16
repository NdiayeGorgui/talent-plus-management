package com.gogo.candidat_service.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lettres_motivation")
public class LettreMotivation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String contenu;
    private String fichierUrl; // stockage fichier
    @Lob
    private String contenuTexte; // texte brut si stocké directement

    private LocalDateTime dateDepot = LocalDateTime.now();
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private Candidat candidat;


}

