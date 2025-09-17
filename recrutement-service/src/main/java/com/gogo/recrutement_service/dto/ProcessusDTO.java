package com.gogo.recrutement_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProcessusDTO {
    private Long id;
    private Long candidatId;
    private Long offreId;
    private String statut;
    private LocalDateTime dateMaj;

}
