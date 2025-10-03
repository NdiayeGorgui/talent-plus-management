package com.gogo.recrutement_service.service;

import com.gogo.base_domaine_service.dto.NotificationMessage;
import com.gogo.recrutement_service.dto.HistoriqueDTO;
import com.gogo.recrutement_service.dto.ProcessusDTO;
import com.gogo.recrutement_service.enums.StatutProcessus;
import com.gogo.recrutement_service.enums.TypeCandidature;
import com.gogo.recrutement_service.exception.CandidatNotFoundException;
import com.gogo.recrutement_service.exception.OffreNotFoundException;
import com.gogo.recrutement_service.exception.ProcessusNotFoundException;
import com.gogo.recrutement_service.exception.NotificationException;
import com.gogo.recrutement_service.mapper.HistoriqueMapper;
import com.gogo.recrutement_service.mapper.ProcessusMapper;
import com.gogo.recrutement_service.model.Historique;
import com.gogo.recrutement_service.model.Processus;
import com.gogo.recrutement_service.repository.HistoriqueRepository;
import com.gogo.recrutement_service.repository.ProcessusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
                                  @Qualifier("offreWebClient") WebClient offreWebClient) {
        this.processusRepository = processusRepository;
        this.historiqueRepository = historiqueRepository;
        this.candidatWebClient = candidatWebClient;
        this.offreWebClient = offreWebClient;
    }

    // Validation candidat
    private void validateCandidatExists(Long candidatId) throws CandidatNotFoundException {
        try {
            candidatWebClient.get()
                    .uri("/{id}", candidatId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception e) {
            log.error("❌ Candidat introuvable avec id={}", candidatId, e);
            throw new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId);
        }
    }

    // Validation offre
    private void validateOffreExists(Long offreId) throws OffreNotFoundException {
        try {
            offreWebClient.get()
                    .uri("/{id}", offreId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception e) {
            log.error("❌ Offre introuvable avec id={}", offreId, e);
            throw new OffreNotFoundException("Offre non trouvée avec id " + offreId);
        }
    }

    @Override
    public ProcessusDTO createProcessus(Long candidatId, Long offreId) throws NotificationException, CandidatNotFoundException, OffreNotFoundException {
        validateCandidatExists(candidatId);
        validateOffreExists(offreId);

        log.info("➡️ Création d'un nouveau processus pour candidatId={} et offreId={}", candidatId, offreId);

        Processus processus = new Processus();
        processus.setCandidatId(candidatId);
        processus.setOffreId(offreId);
        processus.setTypeCandidature(TypeCandidature.OFFRE);
        processus.setStatut(StatutProcessus.RECU.name());
        processus.setDateMaj(LocalDateTime.now());

        Processus saved = processusRepository.save(processus);
        log.info("✅ Processus sauvegardé avec ID={}, statut={}", saved.getId(), saved.getStatut());

        // Historique initial
        Historique historique = new Historique();
        historique.setProcessusId(saved.getId());
        historique.setStatut(StatutProcessus.RECU.name());
        historique.setDateChangement(LocalDateTime.now());
        historique.setRecruteur("SYSTEM"); // à remplacer par user connecté
        historiqueRepository.save(historique);

        // Notification RabbitMQ
        NotificationMessage msg = new NotificationMessage(saved.getCandidatId(), "RECU", LocalDateTime.now());
        try {
            log.info("📨 Notification prête à être envoyée au candidatId={}, statut={}", saved.getCandidatId(), "RECU");
            amqpTemplate.convertAndSend("notificationExchange", "candidat.statut", msg);
            log.info("🚀 Notification envoyée à RabbitMQ pour candidatId={}", saved.getCandidatId());
        } catch (Exception e) {
            log.error("⚠️ Impossible d’envoyer la notification RabbitMQ pour candidatId={}", saved.getCandidatId(), e);
            throw new NotificationException("Échec de l’envoi de la notification pour le candidat " + saved.getCandidatId());
        }

        return ProcessusMapper.toDTO(saved);
    }

    @Override
    public ProcessusDTO updateStatut(Long processusId, String nouveauStatut) throws NotificationException, ProcessusNotFoundException {
        Processus processus = processusRepository.findById(processusId)
                .orElseThrow(() -> new ProcessusNotFoundException("Processus introuvable avec id " + processusId));

        processus.setStatut(nouveauStatut);
        processus.setDateMaj(LocalDateTime.now());

        Processus saved = processusRepository.save(processus);

        // Historique
        Historique historique = new Historique();
        historique.setProcessusId(saved.getId());
        historique.setStatut(nouveauStatut);
        historique.setDateChangement(LocalDateTime.now());
        historique.setRecruteur("SYSTEM");
        historiqueRepository.save(historique);

        // Notification RabbitMQ
        NotificationMessage msg = new NotificationMessage(saved.getCandidatId(), nouveauStatut, LocalDateTime.now());
        try {
            log.info("📨 Notification prête à être envoyée au candidatId={}, statut={}", saved.getCandidatId(), nouveauStatut);
            amqpTemplate.convertAndSend("notificationExchange", "candidat.statut", msg);
            log.info("🚀 Notification envoyée à RabbitMQ pour candidatId={}", saved.getCandidatId());
        } catch (Exception e) {
            log.error("⚠️ Impossible d’envoyer la notification RabbitMQ pour candidatId={}", saved.getCandidatId(), e);
            throw new NotificationException("Échec de l’envoi de la notification pour le candidat " + saved.getCandidatId());
        }

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

    @Override
    public ProcessusDTO createProcessusSpontane(Long candidatId, String messageMotivation)
            throws NotificationException, CandidatNotFoundException {

        validateCandidatExists(candidatId);

        log.info("➡️ Création d'un processus SPONTANÉ pour candidatId={}", candidatId);

        Processus processus = new Processus();
        processus.setCandidatId(candidatId);
        processus.setOffreId(null); // 👈 pas d’offre
        processus.setTypeCandidature(TypeCandidature.SPONTANEE);
        processus.setStatut(StatutProcessus.RECU.name());
        processus.setDateMaj(LocalDateTime.now());

        Processus saved = processusRepository.save(processus);

        // Historique initial
        Historique historique = new Historique();
        historique.setProcessusId(saved.getId());
        historique.setStatut(StatutProcessus.RECU.name());
        historique.setDateChangement(LocalDateTime.now());
        historique.setRecruteur("SYSTEM");
        historiqueRepository.save(historique);

        // Notification
        NotificationMessage msg = new NotificationMessage(saved.getCandidatId(), "SPONTANEE", LocalDateTime.now());
        try {
            amqpTemplate.convertAndSend("notificationExchange", "candidat.statut", msg);
        } catch (Exception e) {
            throw new NotificationException("Échec de l’envoi de la notification pour le candidat " + saved.getCandidatId());
        }

        return ProcessusMapper.toDTO(saved);
    }

    @Override
    public ProcessusDTO lierCandidatureSpontanee(Long processusId, Long offreId)
            throws ProcessusNotFoundException, OffreNotFoundException, NotificationException {

        // Vérifier que le processus existe
        Processus processus = processusRepository.findById(processusId)
                .orElseThrow(() -> new ProcessusNotFoundException("Processus introuvable avec id " + processusId));

        // Vérifier que c'était bien une candidature spontanée
        if (processus.getTypeCandidature() != TypeCandidature.SPONTANEE) {
            throw new IllegalStateException("Ce processus n'est pas une candidature spontanée !");
        }

        // Vérifier que l'offre existe
        validateOffreExists(offreId);

        // Mise à jour
        processus.setOffreId(offreId);
        processus.setTypeCandidature(TypeCandidature.OFFRE);
        processus.setDateMaj(LocalDateTime.now());

        Processus saved = processusRepository.save(processus);

        // Historique
        Historique historique = new Historique();
        historique.setProcessusId(saved.getId());
        historique.setStatut("LIÉ_OFFRE");
        historique.setDateChangement(LocalDateTime.now());
        historique.setRecruteur("SYSTEM");
        historiqueRepository.save(historique);

        // Notification
        NotificationMessage msg = new NotificationMessage(
                saved.getCandidatId(), "LIÉ_OFFRE", LocalDateTime.now()
        );
        amqpTemplate.convertAndSend("notificationExchange", "candidat.statut", msg);

        return ProcessusMapper.toDTO(saved);
    }

    @Override
    public List<ProcessusDTO> getCandidaturesSpontanees() {
        return processusRepository.findAll().stream()
                .filter(p -> p.getTypeCandidature() == TypeCandidature.SPONTANEE)
                .map(ProcessusMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessusDTO> findByTypeCandidature(TypeCandidature typeCandidature) {
        return processusRepository.findByTypeCandidature(typeCandidature)
                .stream()
                .map(ProcessusMapper::toDTO)  // ✅ appel statique
                .collect(Collectors.toList());
    }




}
