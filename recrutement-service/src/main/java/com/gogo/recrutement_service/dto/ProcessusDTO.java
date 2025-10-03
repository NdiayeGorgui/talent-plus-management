package com.gogo.recrutement_service.dto;

import com.gogo.recrutement_service.enums.StatutProcessus;
import com.gogo.recrutement_service.enums.TypeCandidature;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProcessusDTO {
    private Long id;
    private Long candidatId;
    private Long offreId;
    private TypeCandidature typeCandidature;
    private StatutProcessus statut;
    private LocalDateTime dateMaj;

}
