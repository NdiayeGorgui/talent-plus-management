package com.gogo.offre_emploi_service.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    private boolean active = true; // pour clôturer l'offre

    // getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDatePublication() { return datePublication; }
    public void setDatePublication(LocalDateTime datePublication) { this.datePublication = datePublication; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

