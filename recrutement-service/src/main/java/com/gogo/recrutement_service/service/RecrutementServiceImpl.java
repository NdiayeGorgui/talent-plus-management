package com.gogo.recrutement_service.service;

import com.gogo.base_domaine_service.dto.NotificationMessage;
import com.gogo.recrutement_service.dto.HistoriqueDTO;
import com.gogo.recrutement_service.dto.ProcessusDTO;
import com.gogo.recrutement_service.mapper.HistoriqueMapper;
import com.gogo.recrutement_service.mapper.ProcessusMapper;
import com.gogo.recrutement_service.model.Historique;
import com.gogo.recrutement_service.model.Processus;
import com.gogo.recrutement_service.repository.HistoriqueRepository;
import com.gogo.recrutement_service.repository.ProcessusRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecrutementServiceImpl implements RecrutementService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    private final ProcessusRepository processusRepository;
    private final HistoriqueRepository historiqueRepository;
    private final WebClient candidatWebClient;
    private final WebClient offreWebClient;

    public RecrutementServiceImpl(ProcessusRepository processusRepository,
                                  HistoriqueRepository historiqueRepository,
                                  @Qualifier("candidatWebClient") WebClient candidatWebClient,
                                  @Qualifier("offreWebClient") WebClient offreWebClient
                                  ) {
        this.processusRepository = processusRepository;
        this.historiqueRepository = historiqueRepository;
        this.candidatWebClient = candidatWebClient;
        this.offreWebClient = offreWebClient;
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
        log.info("➡️ Création d'un nouveau processus pour candidatId={} et offreId={}", candidatId, offreId);
        Processus processus = new Processus();
        processus.setCandidatId(candidatId);
        processus.setOffreId(offreId);
        processus.setStatut("RECU");
        processus.setDateMaj(LocalDateTime.now());

        Processus saved = processusRepository.save(processus);
        log.info("✅ Processus sauvegardé avec ID={}, statut={}", saved.getId(), saved.getStatut());

        Historique historique = new Historique();
        historique.setProcessusId(saved.getId());
        historique.setStatut("RECU");
        historique.setDateChangement(LocalDateTime.now());
        historique.setRecruteur("SYSTEM"); // à remplacer par user connecté
        historiqueRepository.save(historique);

        //  Publier une notification au candidat
        NotificationMessage msg = new NotificationMessage(
                saved.getCandidatId(),
                "RECU", // statut initial
                LocalDateTime.now()
        );
        log.info("📨 Notification prête à être envoyée au candidatId={}, statut={}", saved.getCandidatId(), "RECU");
        amqpTemplate.convertAndSend("notificationExchange", "candidat.statut", msg);
        log.info("🚀 Notification envoyée à RabbitMQ pour candidatId={}", saved.getCandidatId());

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

        //  Publier un message RabbitMQ
        NotificationMessage msg = new NotificationMessage(
                saved.getCandidatId(),
                nouveauStatut,
                LocalDateTime.now()
        );
        // 📝 Logs avant et après l’envoi
        log.info("📨 Notification prête à être envoyée au candidatId={}, statut={}", saved.getCandidatId(), nouveauStatut);
        amqpTemplate.convertAndSend("notificationExchange", "candidat.statut", msg);
        log.info("🚀 Notification envoyée à RabbitMQ pour candidatId={}", saved.getCandidatId());

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
