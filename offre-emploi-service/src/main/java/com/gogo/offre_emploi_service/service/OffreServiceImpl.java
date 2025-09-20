package com.gogo.offre_emploi_service.service;

import com.gogo.offre_emploi_service.dto.OffreDTO;
import com.gogo.offre_emploi_service.exception.OffreNotFoundException;
import com.gogo.offre_emploi_service.mapper.OffreMapper;
import com.gogo.offre_emploi_service.model.Offre;
import com.gogo.offre_emploi_service.repository.OffreRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffreServiceImpl implements OffreService {

    private final OffreRepository offreRepository;
    private final WebClient recruteurWebClient;

    public OffreServiceImpl(OffreRepository offreRepository, WebClient recruteurWebClient) {
        this.offreRepository = offreRepository;
        this.recruteurWebClient = recruteurWebClient;
    }

    @Override
    public OffreDTO createOffre(OffreDTO dto) {
        // Vérifier si le recruteur existe via l’autre microservice
        validateRecruteurExists(dto.getRecruteurId());
        Offre offre = OffreMapper.fromDTO(dto);
        return OffreMapper.toDTO(offreRepository.save(offre));
    }

    @Override
    public OffreDTO updateOffre(Long id, OffreDTO dto) throws OffreNotFoundException {
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new OffreNotFoundException("Offre not available with id: " + id));

        // Vérifier si on souhaite changer le recruteur
        if (dto.getRecruteurId() != null && !dto.getRecruteurId().equals(offre.getRecruteurId())) {
            validateRecruteurExists(dto.getRecruteurId());
            offre.setRecruteurId(dto.getRecruteurId());
        }

        // Mettre à jour les champs principaux
        offre.setTitre(dto.getTitre());
        offre.setDescription(dto.getDescription());
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

    private void validateRecruteurExists(Long recruteurId) {
        recruteurWebClient.get()
                .uri("/{id}", recruteurId)
                .retrieve()
                .onStatus(status -> status.value() == 404, r -> Mono.error(new RuntimeException("Recruteur introuvable")))
                .bodyToMono(Void.class)
                .block(); // blocage acceptable si synch service simple; sinon faire tout réactif
    }
}
