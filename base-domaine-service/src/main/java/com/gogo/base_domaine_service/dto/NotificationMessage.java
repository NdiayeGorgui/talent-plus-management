package com.gogo.base_domaine_service.dto;

import java.time.LocalDateTime;

public record NotificationMessage(
        Long candidatId,
        String nouveauStatut,
        LocalDateTime dateChangement
) {}

