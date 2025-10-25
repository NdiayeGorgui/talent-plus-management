package com.gogo.utilisateur_service.service;

import com.gogo.notification_service.dto.UserDto;
import com.gogo.utilisateur_service.model.Utilisateur;

import java.util.Optional;

public interface UtilisateurService {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Utilisateur saveUtilisateur(Utilisateur utilisateur);
    Optional<UserDto> getByEmail(String email);
}
