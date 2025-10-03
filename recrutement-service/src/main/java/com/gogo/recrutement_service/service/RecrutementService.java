package com.gogo.recrutement_service.service;

import com.gogo.recrutement_service.dto.HistoriqueDTO;
import com.gogo.recrutement_service.dto.ProcessusDTO;
import com.gogo.recrutement_service.enums.TypeCandidature;
import com.gogo.recrutement_service.exception.CandidatNotFoundException;
import com.gogo.recrutement_service.exception.NotificationException;
import com.gogo.recrutement_service.exception.OffreNotFoundException;
import com.gogo.recrutement_service.exception.ProcessusNotFoundException;

import java.util.List;

public interface RecrutementService {
    ProcessusDTO createProcessus(Long candidatId, Long offreId)
            throws NotificationException, CandidatNotFoundException, OffreNotFoundException;
    ProcessusDTO updateStatut(Long processusId, String nouveauStatut)
            throws NotificationException, ProcessusNotFoundException;
    List<ProcessusDTO> getAllProcessus();
    List<ProcessusDTO> getByCandidat(Long candidatId);
    List<ProcessusDTO> getByOffre(Long offreId);
    List<HistoriqueDTO> getHistorique(Long processusId);
    ProcessusDTO createProcessusSpontane(Long candidatId, String messageMotivation)
            throws NotificationException,CandidatNotFoundException;
    ProcessusDTO lierCandidatureSpontanee(Long processusId, Long offreId)
            throws ProcessusNotFoundException, OffreNotFoundException, NotificationException;
    List<ProcessusDTO> getCandidaturesSpontanees();
    List<ProcessusDTO> findByTypeCandidature(TypeCandidature typeCandidature);



}
