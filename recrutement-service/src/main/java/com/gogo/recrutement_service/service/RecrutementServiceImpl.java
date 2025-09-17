package com.gogo.recrutement_service.service;

import com.gogo.recrutement_service.dto.HistoriqueDTO;
import com.gogo.recrutement_service.dto.ProcessusDTO;
import com.gogo.recrutement_service.mapper.HistoriqueMapper;
import com.gogo.recrutement_service.mapper.ProcessusMapper;
import com.gogo.recrutement_service.model.Historique;
import com.gogo.recrutement_service.model.Processus;
import com.gogo.recrutement_service.repository.HistoriqueRepository;
import com.gogo.recrutement_service.repository.ProcessusRepository;
import com.gogo.recrutement_service.service.RecrutementService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecrutementServiceImpl implements RecrutementService {

    private final ProcessusRepository processusRepository;
    private final HistoriqueRepository historiqueRepository;
    private final WebClient candidatWebClient;
    private final WebClient offreWebClient;

    public RecrutementServiceImpl(ProcessusRepository processusRepository,
                                  HistoriqueRepository historiqueRepository,
                                  WebClient.Builder webClientBuilder) {
        this.processusRepository = processusRepository;
        this.historiqueRepository = historiqueRepository;
        this.candidatWebClient = webClientBuilder.baseUrl("http://candidat-service/api/v1/candidats").build();
        this.offreWebClient = webClientBuilder.baseUrl("http://offre-service/api/v1/offres").build();
    }

    private void validateCandidatExists(Long candidatId) {
        candidatWebClient.get()
                .uri("/{id}", candidatId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private void validateOffreExists(Long offreId) {
        offreWebClient.get()
                .uri("/{id}", offreId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public ProcessusDTO createProcessus(Long candidatId, Long offreId) {
        validateCandidatExists(candidatId);
        validateOffreExists(offreId);

        Processus processus = new Processus();
        processus.setCandidatId(candidatId);
        processus.setOffreId(offreId);
        processus.setStatut("RECU");
        processus.setDateMaj(LocalDateTime.now());

        Processus saved = processusRepository.save(processus);

        Historique historique = new Historique();
        historique.setProcessusId(saved.getId());
        historique.setStatut("RECU");
        historique.setDateChangement(LocalDateTime.now());
        historique.setRecruteur("SYSTEM"); // à remplacer par user connecté
        historiqueRepository.save(historique);

        return ProcessusMapper.toDTO(saved);
    }

    @Override
    public ProcessusDTO updateStatut(Long processusId, String nouveauStatut) {
        Processus processus = processusRepository.findById(processusId)
                .orElseThrow(() -> new RuntimeException("Processus introuvable"));

        processus.setStatut(nouveauStatut);
        processus.setDateMaj(LocalDateTime.now());

        Processus saved = processusRepository.save(processus);

        Historique historique = new Historique();
        historique.setProcessusId(saved.getId());
        historique.setStatut(nouveauStatut);
        historique.setDateChangement(LocalDateTime.now());
        historique.setRecruteur("SYSTEM");
        historiqueRepository.save(historique);

        return ProcessusMapper.toDTO(saved);
    }

    @Override
    public List<ProcessusDTO> getAllProcessus() {
        return processusRepository.findAll().stream()
                .map(ProcessusMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessusDTO> getByCandidat(Long candidatId) {
        return processusRepository.findByCandidatId(candidatId).stream()
                .map(ProcessusMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessusDTO> getByOffre(Long offreId) {
        return processusRepository.findByOffreId(offreId).stream()
                .map(ProcessusMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistoriqueDTO> getHistorique(Long processusId) {
        return historiqueRepository.findByProcessusId(processusId).stream()
                .map(HistoriqueMapper::toDTO)
                .collect(Collectors.toList());
    }
}
