package com.gogo.offre_emploi_service.service;

import com.gogo.offre_emploi_service.dto.OffreCountDTO;
import com.gogo.offre_emploi_service.dto.OffreDTO;
import com.gogo.offre_emploi_service.exception.OffreNotFoundException;
import com.gogo.offre_emploi_service.mapper.OffreMapper;
import com.gogo.offre_emploi_service.model.Offre;
import com.gogo.offre_emploi_service.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffreServiceImpl implements OffreService {

    private final OffreRepository offreRepository;
    private final WebClient recruteurWebClient;
    private final WebClient employeurWebClient;

    // L’email fixe du recruteur admin
    private static final String ADMIN_RECRUTEUR_EMAIL = "adminrecruteur@talentplus.com";

    public OffreServiceImpl(OffreRepository offreRepository,
                            @Qualifier("recruteurWebClient") WebClient recruteurWebClient,
                            @Qualifier("employeurWebClient") WebClient employeurWebClient) {
        this.offreRepository = offreRepository;
        this.recruteurWebClient = recruteurWebClient;
        this.employeurWebClient = employeurWebClient;
    }

    @Override
    public OffreDTO createOffre(OffreDTO dto) {
        try {
            // ✅ Si aucun recruteurId n’est fourni (cas employeur)
            if (dto.getRecruteurId() == null) {
                Long adminRecruteurId = fetchRecruteurIdByEmail(ADMIN_RECRUTEUR_EMAIL);
                dto.setRecruteurId(adminRecruteurId);
                System.out.println("🔁 Recruteur admin assigné automatiquement : ID = " + adminRecruteurId);
            } else {
                // ✅ Sinon on valide que le recruteur existe
                validateRecruteurExists(dto.getRecruteurId());
            }

            Offre offre = OffreMapper.fromDTO(dto);
            Offre saved = offreRepository.save(offre);
            return OffreMapper.toDTO(saved);

        } catch (Exception ex) {
            System.err.println("❌ Erreur lors de la création de l’offre : " + ex.getMessage());
            throw new RuntimeException("Impossible de créer l’offre : " + ex.getMessage());
        }
    }

    @Override
    public OffreDTO updateOffre(Long id, OffreDTO dto) throws OffreNotFoundException {
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new OffreNotFoundException("Offre not available with id: " + id));

        // ✅ Vérifie si recruteurId est changé et valide
        if (dto.getRecruteurId() != null && !dto.getRecruteurId().equals(offre.getRecruteurId())) {
            validateRecruteurExists(dto.getRecruteurId());
            offre.setRecruteurId(dto.getRecruteurId());
        }

        // ✅ Mise à jour des champs
        offre.setTitre(dto.getTitre());
        offre.setDescription(dto.getDescription());
        offre.setDateFinAffichage(dto.getDateFinAffichage());
        offre.setCategorie(dto.getCategorie());
        offre.setVille(dto.getVille());
        offre.setPays(dto.getPays());
        offre.setActive(dto.isActive());

        if (dto.getDatePublication() != null) {
            offre.setDatePublication(dto.getDatePublication());
        }

        Offre updated = offreRepository.save(offre);
        return OffreMapper.toDTO(updated);
    }

    @Override
    public void closeOffre(Long id) throws OffreNotFoundException {
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new OffreNotFoundException("Offre not available with id: " + id));
        offre.setActive(false);
        offreRepository.save(offre);
    }

    @Override
    public void openOffre(Long id) throws OffreNotFoundException {
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new OffreNotFoundException("Offre non trouvée"));
        offre.setActive(true);
        offreRepository.save(offre);
    }

    @Override
    public List<OffreDTO> getAllOffres() {
        return offreRepository.findAll().stream()
                .map(OffreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OffreDTO getOffreById(Long id) throws OffreNotFoundException {
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new OffreNotFoundException("Offre not available with id: " + id));
        return OffreMapper.toDTO(offre);
    }

    @Override
    public void deleteOffre(Long id) throws OffreNotFoundException {
        if (!offreRepository.existsById(id)) {
            throw new OffreNotFoundException("Offre not available with id: " + id);
        }
        offreRepository.deleteById(id);
    }

    @Override
    public List<OffreDTO> getOffresByRecruteur(Long recruteurId) {
        validateRecruteurExists(recruteurId);
        return offreRepository.findByRecruteurId(recruteurId).stream()
                .map(OffreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findOffreIdsByRecruteurId(Long recruteurId) {
        return offreRepository.findByRecruteurId(recruteurId)
                .stream()
                .map(Offre::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<OffreDTO> getOffresByEmployeur(Long employeurId) {
        validateEmployeurExists(employeurId);
        return offreRepository.findByEmployeurId(employeurId).stream()
                .map(OffreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findOffreIdsByEmployeurId(Long employeurId) {
        return offreRepository.findByEmployeurId(employeurId)
                .stream()
                .map(Offre::getId)
                .collect(Collectors.toList());
    }

    /**
     * ✅ Vérifie l'existence d'un recruteur à partir de son ID
     */
    private void validateRecruteurExists(Long recruteurId) {
        try {
            recruteurWebClient.get()
                    .uri("/{id}", recruteurId)
                    .retrieve()
                    .onStatus(status -> status.value() == 404,
                            r -> Mono.error(new RuntimeException("Recruteur introuvable avec id " + recruteurId)))
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors de la vérification du recruteur : " + ex.getMessage());
        }
    }

    private void validateEmployeurExists(Long employeurId) {
        try {
            employeurWebClient.get()
                    .uri("/{id}", employeurId)
                    .retrieve()
                    .onStatus(status -> status.value() == 404,
                            r -> Mono.error(new RuntimeException("Employeur introuvable avec id " + employeurId)))
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors de la vérification de l employeur : " + ex.getMessage());
        }
    }

    /**
     * ✅ Récupère dynamiquement l’ID du recruteur admin via son email
     */
    private Long fetchRecruteurIdByEmail(String email) {
        try {
            Long id = recruteurWebClient.get()
                    .uri("/email/{email}/id", email)
                    .retrieve()
                    .bodyToMono(Long.class)
                    .block(); // bloquant (ok dans ce cas simple)

            if (id == null) {
                throw new RuntimeException("Aucun recruteur trouvé avec l’email : " + email);
            }

            System.out.println("✅ Recruteur admin trouvé avec ID = " + id);
            return id;

        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors de la récupération du recruteur admin : " + ex.getMessage());
        }
    }
    @Override
    public List<OffreCountDTO> countOffresByRecruteur() {
        return offreRepository.countOffresByRecruteur().stream()
                .map(obj -> new OffreCountDTO(
                        ((Number) obj[0]).longValue(),  // recruteurId
                        null,                           // nom — on le complétera plus tard
                        ((Number) obj[1]).longValue()   // nombreOffres
                ))
                .toList();
    }
    @Override
    public List<OffreCountDTO> countOffresByEmployeur() {
        return offreRepository.countOffresByEmployeur().stream()
                .map(obj -> new OffreCountDTO(
                        ((Number) obj[0]).longValue(),  // employeurId
                        null,
                        ((Number) obj[1]).longValue()
                ))
                .toList();
    }

    /**
     * 🔁 Tâche planifiée : désactive automatiquement les offres expirées chaque jour à minuit
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void closeExpiredOffres() {
        List<Offre> expiredOffres = offreRepository.findByActiveTrueAndDateFinAffichageBefore(LocalDateTime.now());
        expiredOffres.forEach(o -> o.setActive(false));
        offreRepository.saveAll(expiredOffres);

        if (!expiredOffres.isEmpty()) {
            System.out.println("✅ " + expiredOffres.size() + " offres clôturées automatiquement.");
        }
    }
}
