package com.gogo.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;             // ID de l'utilisateur
    private String email;        // email pour retrouver le candidat
    private String username;     // optionnel, si tu veux afficher le nom
    private List<String> roles;  // optionnel, si tu veux filtrer selon le rôle
}
