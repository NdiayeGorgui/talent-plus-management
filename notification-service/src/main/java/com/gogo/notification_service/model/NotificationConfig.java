package com.gogo.notification_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class NotificationConfig {
    @Id
    @GeneratedValue
    private Long id;
    private String typeEvenement; // ex: "ACCEPTE", "REFUSE"
    private boolean actif;
}

