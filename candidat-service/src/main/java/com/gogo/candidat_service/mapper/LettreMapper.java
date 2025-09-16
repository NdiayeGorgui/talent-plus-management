package com.gogo.candidat_service.mapper;

import com.gogo.candidat_service.dto.LettreDTO;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.LettreMotivation;

public class LettreMapper {

    public static LettreMotivation fromDTO(LettreDTO dto, Candidat candidat) {
        LettreMotivation lettre = new LettreMotivation();
        lettre.setTitre(dto.getTitre());
        lettre.setContenu(dto.getContenu());
        lettre.setCandidat(candidat);
        lettre.setVersion(dto.getVersion());
        lettre.setDateDepot(dto.getDateDepot());
        return lettre;
    }

    public static LettreDTO toDTO(LettreMotivation lettre) {
        LettreDTO dto = new LettreDTO();
        dto.setId(lettre.getId());
        dto.setTitre(lettre.getTitre());
        dto.setContenu(lettre.getContenu());
        dto.setFichierUrl(lettre.getFichierUrl()); // si tu veux exposer le chemin du fichier
        dto.setDateDepot(lettre.getDateDepot());
        dto.setVersion(lettre.getVersion()); // si tu as un champ revision
        return dto;
    }

}

