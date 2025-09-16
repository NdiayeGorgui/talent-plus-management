package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CvDTO;
import com.gogo.candidat_service.model.CV;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CvService {
    CvDTO uploadCv(Long candidatId, MultipartFile file, String titre) throws IOException;
    CvDTO replaceCv(Long cvId, MultipartFile file, String titre) throws IOException;
    List<CvDTO> getCvsByCandidat(Long candidatId);
    void deleteCv(Long cvId);
}
