package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.LettreDTO;
import com.gogo.candidat_service.model.LettreMotivation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LettreMotivationService {
    LettreDTO  addLettre(Long candidatId, LettreDTO dto);
    List<LettreDTO> getLettresByCandidat(Long candidatId);
    void deleteLettre(Long lettreId);
    LettreDTO uploadLettre(Long candidatId, MultipartFile file, String titre) throws IOException;
    LettreDTO replaceLettre(Long lettreId, MultipartFile file, String titre) throws IOException;

}
