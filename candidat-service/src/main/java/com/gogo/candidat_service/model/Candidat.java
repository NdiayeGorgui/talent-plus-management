package com.gogo.candidat_service.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @OneToOne(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private MetadonneeRH metadonneeRH;

    // Getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<CV> getCvs() {
        return cvs;
    }

    public void setCvs(List<CV> cvs) {
        this.cvs = cvs;
    }

    public List<LettreMotivation> getLettres() {
        return lettres;
    }

    public void setLettres(List<LettreMotivation> lettres) {
        this.lettres = lettres;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public MetadonneeRH getMetadonneeRH() {
        return metadonneeRH;
    }

    public void setMetadonneeRH(MetadonneeRH metadonneeRH) {
        this.metadonneeRH = metadonneeRH;
    }
}
