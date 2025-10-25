package com.gogo.notification_service.model;

import com.gogo.notification_service.enums.ReceiverType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long receiverId;             // ID de l'utilisateur (candidat, recruteur...)

    @Enumerated(EnumType.STRING)
    private ReceiverType receiverType;   // CANDIDAT, RECRUTEUR…
    // Type de l'événement : ACCEPTE, REFUSE, etc.
    @Column(nullable = false)
    private String typeEvenement;

    // Contenu du message envoyé
    @Column(nullable = false, length = 500)
    private String message;
    private boolean isRead = false;
    private LocalDateTime timestamp = LocalDateTime.now();
}
