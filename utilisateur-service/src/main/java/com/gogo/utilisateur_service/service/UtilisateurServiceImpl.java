package com.gogo.utilisateur_service.service;

import com.gogo.notification_service.dto.UserDto;
import com.gogo.utilisateur_service.model.Utilisateur;
import com.gogo.utilisateur_service.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public boolean existsByUsername(String username) {
        return utilisateurRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return utilisateurRepository.existsByEmail(email);
    }

    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }
    @Override
    public Optional<UserDto> getByEmail(String email) {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        return Optional.of(new com.gogo.notification_service.dto.UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRoles().stream()
                        .map(role -> role.getRole().name()) // <-- ici on prend le nom de l'enum
                        .toList()
        ));
    }




}
