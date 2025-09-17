package com.gogo.recrutement_service.service;

import com.gogo.recrutement_service.dto.HistoriqueDTO;
import com.gogo.recrutement_service.dto.ProcessusDTO;

import java.util.List;

public interface RecrutementService {
    ProcessusDTO createProcessus(Long candidatId, Long offreId);
    ProcessusDTO updateStatut(Long processusId, String nouveauStatut);
    List<ProcessusDTO> getAllProcessus();
    List<ProcessusDTO> getByCandidat(Long candidatId);
    List<ProcessusDTO> getByOffre(Long offreId);
    List<HistoriqueDTO> getHistorique(Long processusId);
}
