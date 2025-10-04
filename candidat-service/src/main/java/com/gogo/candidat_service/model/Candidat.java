package com.gogo.candidat_service.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gogo.candidat_service.enums.NiveauEtude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "candidats")
public class Candidat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    private String telephone;
    private LocalDate dateNaissance;
    private String adresse;
    @Enumerated(EnumType.STRING)
    private NiveauEtude niveauEtude;


    // Relations

    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CV> cvs = new ArrayList<>();

    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LettreMotivation> lettres = new ArrayList<>();

    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Competence> competences = new ArrayList<>();

    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetenceLinguistique> competencesLinguistiques = new ArrayList<>();

    @OneToOne(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private MetadonneeRH metadonneeRH;

}
