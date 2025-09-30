package com.gogo.recrutement_service.dto;

import com.gogo.recrutement_service.enums.StatutProcessus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProcessusDTO {
    private Long id;
    private Long candidatId;
    private Long offreId;
    private StatutProcessus statut;
    private LocalDateTime dateMaj;

}
