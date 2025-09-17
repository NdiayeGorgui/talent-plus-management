package com.gogo.recruteur_service.model;


import jakarta.persistence.*;

@Entity
@Table(name = "recruteurs")
public class Recruteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String email;

    // Pas de relation directe vers Offre (microservice différent)

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
