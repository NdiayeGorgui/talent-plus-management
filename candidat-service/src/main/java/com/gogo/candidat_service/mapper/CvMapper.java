package com.gogo.candidat_service.mapper;

import com.gogo.candidat_service.dto.CvDTO;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.CV;

public class CvMapper {

    public static CV fromDTO(CvDTO dto, Candidat candidat) {
        CV cv = new CV();
        cv.setTitre(dto.getTitre());
        cv.setFichierUrl(dto.getFichierUrl());
        cv.setCandidat(candidat);
        return cv;
    }

    public static CvDTO toDTO(CV cv) {
        CvDTO dto = new CvDTO();
        dto.setId(cv.getId());
        dto.setTitre(cv.getTitre());
        dto.setFichierUrl(cv.getFichierUrl());
        dto.setDateDepot(cv.getDateDepot());
        dto.setVersion(cv.getVersion());
        return dto;
    }
}

