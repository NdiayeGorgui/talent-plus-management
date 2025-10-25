package com.gogo.utilisateur_service.service;

import com.gogo.utilisateur_service.dto.*;
import com.gogo.utilisateur_service.model.Utilisateur;
import com.gogo.utilisateur_service.repository.UtilisateurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
public class ProfilService {

    private final UtilisateurRepository utilisateurRepository;
    private final WebClient candidatWebClient;
    private final WebClient recruteurWebClient;
    private final WebClient offreWebClient;
    private final WebClient adminWebClient;
    private final WebClient employeurWebClient;

    public ProfilService(UtilisateurRepository utilisateurRepository,
                         @Qualifier("candidatWebClient") WebClient candidatWebClient,
                         @Qualifier("recruteurWebClient") WebClient recruteurWebClient,
                         @Qualifier("adminWebClient") WebClient adminWebClient,
                         @Qualifier("employeurWebClient") WebClient employeurWebClient,
                         @Qualifier("offreWebClient") WebClient offreWebClient) {
        this.utilisateurRepository = utilisateurRepository;
        this.candidatWebClient = candidatWebClient;
        this.recruteurWebClient = recruteurWebClient;
        this.adminWebClient=adminWebClient;
        this.employeurWebClient=employeurWebClient;
        this.offreWebClient=offreWebClient;
    }


    public CandidatProfileDTO getCandidatProfile(String username) {
        // 🔍 Étape 1 : Récupération de l'utilisateur
        Utilisateur user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 🎯 Étape 2 : Récupération du rôle principal
        String role = user.getRoles().get(0).getRole().name();

        // 📦 Étape 3 : Création du DTO à retourner
        CandidatProfileDTO dto = new CandidatProfileDTO();
        dto.setRole(role);
        dto.setEmail(user.getEmail()); // Ce champ existe dans Utilisateur


        // 🔁 Étape 4 : Enrichissement si c’est un candidat
        if ("CANDIDAT".equals(role)) {
            enrichirAvecCandidat(dto, user.getEmail());
        } else {
            log.info("Rôle non pris en charge actuellement : {}", role);
        }

        return dto;
    }

    private void enrichirAvecCandidat(CandidatProfileDTO dto, String email) {
        try {
            CandidatResponseDTO candidat = candidatWebClient.get()
                    .uri("/email/" + email)
                    .retrieve()
                    .bodyToMono(CandidatResponseDTO.class)
                    .block();

            if (candidat == null) {
                log.warn("Candidat introuvable pour l'email: {}", email);
                return;
            }

            // 🎯 Copier les données personnelles et enrichies dans UserProfileDTO
            dto.setId(candidat.getId());
            dto.setNom(candidat.getNom());
            dto.setPrenom(candidat.getPrenom());
            dto.setAdresse(candidat.getAdresse());
            dto.setTelephone(candidat.getTelephone());
            dto.setDateNaissance(candidat.getDateNaissance());
            dto.setNiveauEtude(candidat.getNiveauEtude());

            dto.setCompetences(candidat.getCompetences());
            dto.setExperiences(candidat.getExperiences());
            dto.setMetadonneeRH(candidat.getMetadonneeRH());
            dto.setCvs(candidat.getCvs());
            dto.setLettres(candidat.getLettres());
            dto.setCompetenceLinguistiques(candidat.getCompetencesLinguistiques());

        } catch (Exception e) {
            log.error("❌ Erreur lors de la récupération du profil candidat (email: {})", email, e);
        }
    }
    public RecruteurProfileDTO getRecruteurProfile(String username) {
        Utilisateur user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String email = user.getEmail();

        RecruteurProfileDTO dto = new RecruteurProfileDTO();
        dto.setEmail(email);  // de base

        try {
            RecruteurProfileDTO recruteur = recruteurWebClient.get()
                    .uri("/email/" + email) // endpoint à créer dans recruteur-service
                    .retrieve()
                    .bodyToMono(RecruteurProfileDTO.class)
                    .block();

            if (recruteur != null) {
                dto.setId(recruteur.getId());
                dto.setNom(recruteur.getNom());
                dto.setPrenom(recruteur.getPrenom());
                dto.setTelephone(recruteur.getTelephone());
                dto.setPoste(recruteur.getPoste());
                dto.setNiveau(recruteur.getNiveau());

                // Appel au service offre pour enrichir avec les offres
                List<OffreDTO> offres = offreWebClient.get()
                        .uri("/recruteurs/" + recruteur.getId())
                        .retrieve()
                        .bodyToFlux(OffreDTO.class)
                        .collectList()
                        .block();

                dto.setOffres(offres);
            }

        } catch (Exception e) {
            log.error("❌ Erreur lors de la récupération du profil recruteur", e);
        }

        return dto;
    }

    public AdminProfileDTO getAdminProfile(String username) {
        Utilisateur user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String email = user.getEmail();

        AdminProfileDTO dto = new AdminProfileDTO();
        dto.setEmail(email);  // de base

        try {
            AdminProfileDTO admin = adminWebClient.get()
                    .uri("/admin/email/" + email) // endpoint à créer dans recruteur-service
                    .retrieve()
                    .bodyToMono(AdminProfileDTO.class)
                    .block();

            if (admin != null) {
                dto.setId(admin.getId());
                dto.setNom(admin.getNom());
                dto.setPrenom(admin.getPrenom());
                dto.setTelephone(admin.getTelephone());
                dto.setPoste(admin.getPoste());

            }

        } catch (Exception e) {
            log.error("❌ Erreur lors de la récupération du profil admin", e);
        }

        return dto;
    }

    public EmployeurProfileDTO getEmployeurProfile(String username) {
        Utilisateur user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String email = user.getEmail();
        EmployeurProfileDTO dto = new EmployeurProfileDTO();
        dto.setEmailContact(email);

        try {
            // 1️⃣ Appel au service employeur
            EmployeurProfileDTO employeur = employeurWebClient.get()
                    .uri("/email/" + email)
                    .retrieve()
                    .bodyToMono(EmployeurProfileDTO.class)
                    .block();

            if (employeur != null) {
                dto.setId(employeur.getId());
                dto.setNom(employeur.getNom());
                dto.setTelephone(employeur.getTelephone());
                dto.setPoste(employeur.getPoste());
                dto.setEmailContact(employeur.getEmailContact());
            }

            // 2️⃣ Appel au service offre (uniquement si id non null)
            if (dto.getId() != null) {
                List<OffreDTO> offres = offreWebClient.get()
                        .uri("/employeurs/" + dto.getId())
                        .retrieve()
                        .bodyToFlux(OffreDTO.class)
                        .collectList()
                        .block();

                log.info("Offres récupérées pour employeur {} : {}", dto.getId(), offres != null ? offres.size() : 0);
                dto.setOffres(offres);
            } else {
                log.warn("⚠️ Aucun ID employeur trouvé, offres non chargées.");
            }

        } catch (Exception e) {
            log.error("❌ Erreur lors de la récupération du profil employeur (email: {})", email, e);
        }

        return dto;
    }


    public String getUserRole(String username) {
        Utilisateur user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return user.getRoles().get(0).getRole().name(); // ou filtrer si tu veux un rôle particulier
    }


}
