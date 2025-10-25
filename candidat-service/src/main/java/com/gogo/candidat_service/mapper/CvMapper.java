package com.gogo.candidat_service.mapper;

import com.gogo.candidat_service.dto.CvDTO;
import com.gogo.candidat_service.model.CV;
import com.gogo.candidat_service.model.Candidat;

public class CvMapper {

    public static CV fromDTO(CvDTO dto, Candidat candidat) {
        CV cv = new CV();

        // Affecter un titre par défaut si vide ou null
        String titre = (dto.getTitre() == null || dto.getTitre().trim().isEmpty())
                ? "Mon CV"
                : dto.getTitre();
        cv.setTitre(titre);
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

