package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.LettreDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.LettreMotivationNotFoundException;
import com.gogo.candidat_service.model.LettreMotivation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LettreMotivationService {
    LettreDTO  addLettre(Long candidatId, LettreDTO dto) throws CandidatNotFoundException;
    List<LettreDTO> getLettresByCandidat(Long candidatId) throws CandidatNotFoundException;
    void deleteLettre(Long lettreId) throws LettreMotivationNotFoundException;
    LettreDTO uploadLettre(Long candidatId, MultipartFile file, String titre) throws IOException, CandidatNotFoundException;
    LettreDTO replaceLettre(Long lettreId, MultipartFile file, String titre) throws IOException, LettreMotivationNotFoundException;

}
